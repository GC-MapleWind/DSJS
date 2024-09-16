package site.dpbr.dsjs.domain.character.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.dpbr.dsjs.domain.character.domain.Character;
import site.dpbr.dsjs.domain.character.domain.repository.CharacterRepository;
import site.dpbr.dsjs.domain.character.exception.FailToUpdateCharacterInfoException;
import site.dpbr.dsjs.global.success.SuccessCode;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateAllCharacterInfo {
    private final CharacterRepository characterRepository;
    private final FetchCharacterInfo fetchCharacterInfo;

    public String execute() {
        List<Character> characters = characterRepository.findAll();

        characters.parallelStream()
                .forEach(character -> {
                    try {
                        String ocid = character.getOcid();
                        character.updateInfo(fetchCharacterInfo.execute(ocid));
                    } catch (IOException e) {
                        throw new FailToUpdateCharacterInfoException();
                    }
                });

        characterRepository.saveAll(characters);

        return SuccessCode.UPDATE_SUCCESS.getMessage();
    }
}
