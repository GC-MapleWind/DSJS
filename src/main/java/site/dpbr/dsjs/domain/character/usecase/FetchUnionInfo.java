package site.dpbr.dsjs.domain.character.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.dpbr.dsjs.domain.character.domain.Character;
import site.dpbr.dsjs.domain.character.domain.repository.CharacterRepository;
import site.dpbr.dsjs.domain.character.presentation.dto.response.CharacterUnionInfoResponse;
import site.dpbr.dsjs.global.openAPI.Connection;
import site.dpbr.dsjs.global.success.SuccessCode;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FetchUnionInfo {

    private final Connection connection;
    private final ObjectMapper objectMapper;
    private final CharacterRepository characterRepository;

    public String execute() throws IOException {
        List<Character> characterList = characterRepository.findAll();

        for (Character character : characterList) {
            String path = "/user/union?ocid=" + character.getOcid();
            CharacterUnionInfoResponse response = objectMapper.readValue(connection.execute(path), CharacterUnionInfoResponse.class);

            character.updateUnionInfo(response);
            characterRepository.save(character);
        }

        return SuccessCode.FETCH_UNION_INFO_SUCCESS.getMessage();
    }
}
