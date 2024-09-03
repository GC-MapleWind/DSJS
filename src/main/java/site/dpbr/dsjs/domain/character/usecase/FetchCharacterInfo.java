package site.dpbr.dsjs.domain.character.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.dpbr.dsjs.domain.character.domain.Character;
import site.dpbr.dsjs.domain.character.presentation.dto.response.CharacterBasicInfoResponse;
import site.dpbr.dsjs.domain.character.presentation.dto.response.CharacterMuLungInfoResponse;
import site.dpbr.dsjs.domain.character.presentation.dto.response.CharacterStatInfoResponse;
import site.dpbr.dsjs.domain.character.presentation.dto.response.CharacterUnionInfoResponse;
import site.dpbr.dsjs.global.openAPI.Connection;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FetchCharacterInfo {

    private final FetchOcid fetchOcid;
    private final ObjectMapper objectMapper;
    private final Connection connection;

    public Character execute(String characterName, String date) throws IOException {
        String ocid = fetchOcid.execute(characterName);

        CharacterBasicInfoResponse basicInfo = fetchCharacterData("/character/basic?ocid=" + ocid + "&date=" + date, CharacterBasicInfoResponse.class);
        CharacterStatInfoResponse statInfo = fetchCharacterData("/character/stat?ocid=" + ocid + "&date=" + date, CharacterStatInfoResponse.class);
        CharacterUnionInfoResponse unionInfo = fetchCharacterData("/user/union?ocid=" + ocid + "&date=" + date, CharacterUnionInfoResponse.class);
        CharacterMuLungInfoResponse muLungInfo = fetchCharacterData("/character/dojang?ocid=" + ocid + "&date=" + date, CharacterMuLungInfoResponse.class);

        return Character.from(ocid, characterName, basicInfo, unionInfo, statInfo, muLungInfo);
    }

    private <T> T fetchCharacterData(String path, Class<T> responseType) throws IOException {
        String response = connection.execute(path);
        return objectMapper.readValue(response, responseType);
    }
}
