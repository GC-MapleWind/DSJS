package site.dpbr.dsjs.domain.character.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.dpbr.dsjs.domain.character.presentation.dto.response.CharacterOcidResponse;
import site.dpbr.dsjs.global.openAPI.Connection;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class FetchOcid {
    private final Connection connection;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String execute(String characterName) throws IOException {
        String path = "/id?character_name=" + URLEncoder.encode(characterName, StandardCharsets.UTF_8);
        CharacterOcidResponse response = objectMapper.readValue(connection.execute(path), CharacterOcidResponse.class);

        return response.ocid();
    }
}
