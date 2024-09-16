package site.dpbr.dsjs.domain.character.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.dpbr.dsjs.domain.character.presentation.dto.response.*;
import site.dpbr.dsjs.global.openAPI.Connection;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class FetchCharacterInfo {

    private final ObjectMapper objectMapper;
    private final Connection connection;

    private static final long INVALID_LONG_VALUE = -1L;

    public CharacterInfoResponse execute(String ocid, String date) throws IOException {
        CharacterBasicInfoResponse basicInfo = fetchCharacterData("/character/basic?ocid=" + ocid + "&date=" + date, CharacterBasicInfoResponse.class);
        CharacterUnionInfoResponse unionInfo = fetchCharacterData("/user/union?ocid=" + ocid + "&date=" + date, CharacterUnionInfoResponse.class);
        CharacterMuLungInfoResponse muLungInfo = fetchCharacterData("/character/dojang?ocid=" + ocid + "&date=" + date, CharacterMuLungInfoResponse.class);

        return CharacterInfoResponse.of(basicInfo, unionInfo, muLungInfo, calculateMaxCombatPower(ocid, date));
    }

    public CharacterInfoResponse execute(String ocid) throws IOException {
        CharacterBasicInfoResponse basicInfo = fetchCharacterData("/character/basic?ocid=" + ocid, CharacterBasicInfoResponse.class);
        CharacterUnionInfoResponse unionInfo = fetchCharacterData("/user/union?ocid=" + ocid, CharacterUnionInfoResponse.class);
        CharacterMuLungInfoResponse muLungInfo = fetchCharacterData("/character/dojang?ocid=" + ocid, CharacterMuLungInfoResponse.class);

        LocalDateTime now = LocalDateTime.now();
        String date;

        if (now.toLocalTime().isBefore(LocalTime.of(2, 0))) {
            date = now.minusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            date = now.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        long maxCombatPower = Math.max(fetchCharacterData("/character/stat?ocid=" + ocid, CharacterStatInfoResponse.class).finalStats().stream()
                .filter(stat -> stat.statName().equals("전투력"))
                .map(stat -> Long.parseLong(stat.statValue()))
                .findFirst()
                .orElse(INVALID_LONG_VALUE), calculateMaxCombatPower(ocid, date));

        return CharacterInfoResponse.of(basicInfo, unionInfo, muLungInfo, maxCombatPower);
    }

    public <T> T fetchCharacterData(String path, Class<T> responseType) throws IOException {
        String response = connection.execute(path);
        return objectMapper.readValue(response, responseType);
    }

    private String minusDate(String dateStr, int days) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate date = LocalDate.parse(dateStr, formatter);

        LocalDate previousDay = date.minusDays(days);

        return previousDay.format(formatter);
    }

    private long calculateMaxCombatPower(String ocid, String date) throws IOException {
        long maxCombatPower = -1L;

        for (int i = 0; i < 7; i++) {
            maxCombatPower = Math.max(maxCombatPower,
                    fetchCharacterData("/character/stat?ocid=" + ocid + "&date=" + minusDate(date, i), CharacterStatInfoResponse.class).finalStats().stream()
                            .filter(stat -> stat.statName().equals("전투력"))
                            .map(stat -> Long.parseLong(stat.statValue()))
                            .findFirst()
                            .orElse(INVALID_LONG_VALUE));
        }

        return maxCombatPower;
    }
}
