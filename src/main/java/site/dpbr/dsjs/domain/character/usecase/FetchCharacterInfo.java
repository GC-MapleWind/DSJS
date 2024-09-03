package site.dpbr.dsjs.domain.character.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.dpbr.dsjs.domain.character.presentation.dto.response.*;
import site.dpbr.dsjs.global.openAPI.Connection;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FetchCharacterInfo {

    private final FetchOcid fetchOcid;
    private final ObjectMapper objectMapper;
    private final Connection connection;

    public CharacterInfoResponse execute(String ocid, String date) throws IOException {
        CharacterBasicInfoResponse basicInfo = fetchCharacterData("/character/basic?ocid=" + ocid + "&date=" + date, CharacterBasicInfoResponse.class);
        CharacterStatInfoResponse statInfo = fetchCharacterData("/character/stat?ocid=" + ocid + "&date=" + date, CharacterStatInfoResponse.class);
        CharacterUnionInfoResponse unionInfo = fetchCharacterData("/user/union?ocid=" + ocid + "&date=" + date, CharacterUnionInfoResponse.class);
        CharacterMuLungInfoResponse muLungInfo = fetchCharacterData("/character/dojang?ocid=" + ocid + "&date=" + date, CharacterMuLungInfoResponse.class);

        return CharacterInfoResponse.of(basicInfo, unionInfo, statInfo, muLungInfo);
    }

    public <T> T fetchCharacterData(String path, Class<T> responseType) throws IOException {
        String response = connection.execute(path);
        return objectMapper.readValue(response, responseType);
    }
}
