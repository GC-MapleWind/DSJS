package site.dpbr.dsjs.domain.character.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.dpbr.dsjs.domain.character.domain.Character;
import site.dpbr.dsjs.domain.character.domain.repository.CharacterRepository;
import site.dpbr.dsjs.domain.character.exception.CharacterNotFoundException;
import site.dpbr.dsjs.domain.character.presentation.dto.response.SearchCharacterInfoResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchCharacterInfo {
    private final CharacterRepository characterRepository;

    public SearchCharacterInfoResponse execute(String characterName) {
        Character character = characterRepository.findByName(characterName).orElseThrow(CharacterNotFoundException::new);

        List<Character> characters = characterRepository.findAllByOrderByCombatPowerDesc();
        int size = characters.size();
        int ranking = characters.indexOf(character) + 1;

        double percentage = ranking * 100.0 / size;

        return SearchCharacterInfoResponse.of(character, percentage);
    }
}
