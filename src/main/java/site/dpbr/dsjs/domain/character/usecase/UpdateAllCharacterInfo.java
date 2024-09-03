package site.dpbr.dsjs.domain.character.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.dpbr.dsjs.domain.character.domain.repository.CharacterRepository;
import site.dpbr.dsjs.domain.character.exception.FailToUpdateCharacterInfoException;
import site.dpbr.dsjs.global.success.SuccessCode;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UpdateAllCharacterInfo {
    private final CharacterRepository characterRepository;
    private final FetchCharacterInfo fetchCharacterInfo;

    public String execute() {
        characterRepository.findAll().forEach(character -> {
            try {
                String ocid = character.getOcid();
                character.updateInfo(fetchCharacterInfo.execute(ocid));
            } catch (IOException e) {
                throw new FailToUpdateCharacterInfoException();
            }
        });

        return SuccessCode.UPDATE_SUCCESS.getMessage();
    }
}
