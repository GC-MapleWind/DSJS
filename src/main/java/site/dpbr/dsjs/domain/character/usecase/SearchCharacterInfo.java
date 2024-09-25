package site.dpbr.dsjs.domain.character.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.dpbr.dsjs.domain.character.domain.Character;
import site.dpbr.dsjs.domain.character.domain.repository.CharacterRepository;
import site.dpbr.dsjs.domain.character.exception.CharacterNotFoundException;
import site.dpbr.dsjs.domain.character.presentation.dto.response.SearchCharacterInfoResponse;

@Service
@RequiredArgsConstructor
public class SearchCharacterInfo {
	private final CharacterRepository characterRepository;

	public SearchCharacterInfoResponse execute(String characterName) {
		Character character = characterRepository.findByName(characterName)
			.orElseThrow(CharacterNotFoundException::new);

		List<Character> characters = characterRepository.findAllByOrderByCombatPowerDesc();
		int charactersCount = characters.size();

		int sameWorldCount = characterRepository.findAllByWorld(character.getWorld()).size();
		int sameJobCount = characterRepository.findAllByJob(character.getJob()).size();

		double levelRanking =
			(characterRepository.findAllByOrderByLevelDesc().indexOf(character) + 1) * 100.0 / charactersCount;
		double unionRanking =
			(characterRepository.findAllByOrderByUnionLevelDesc().indexOf(character) + 1) * 100.0 / charactersCount;

		double combatPowerRanking = characters.indexOf(character) * 100.0 / charactersCount;

		return SearchCharacterInfoResponse.of(character, charactersCount, sameWorldCount, sameJobCount, levelRanking,
			unionRanking, combatPowerRanking);
	}
}
