package site.dpbr.dsjs.domain.character.usecase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.dpbr.dsjs.domain.character.domain.Character;
import site.dpbr.dsjs.domain.character.domain.repository.CharacterRepository;
import site.dpbr.dsjs.domain.character.presentation.dto.response.SearchCharacterInfoResponse;

@Service
@RequiredArgsConstructor
public class FindAllCharactersInfo {

	private final CharacterRepository characterRepository;

	public List<SearchCharacterInfoResponse> execute() {
		ArrayList<SearchCharacterInfoResponse> responses = new ArrayList<>();

		List<Character> characters = characterRepository.findAll();
		List<Character> charactersByCombatPower = characterRepository.findAllByOrderByCombatPowerDesc();
		List<Character> charactersByLevel = characterRepository.findAllByOrderByLevelDesc();
		List<Character> charactersByUnionLevel = characterRepository.findAllByOrderByUnionLevelDesc();

		int charactersCount = characters.size();

		for (Character character : characters) {

			int sameWorldCount = characterRepository.findAllByWorld(character.getWorld()).size();
			int sameJobCount = characterRepository.findAllByJob(character.getJob()).size();

			double levelRanking = (charactersByLevel.indexOf(character) + 1) * 100.0 / charactersCount;
			double unionRanking = (charactersByUnionLevel.indexOf(character) + 1) * 100.0 / charactersCount;
			double combatPowerRanking = (charactersByCombatPower.indexOf(character) + 1) * 100.0 / charactersCount;

			responses.add(
				SearchCharacterInfoResponse.of(character, charactersCount, sameWorldCount, sameJobCount, levelRanking,
					unionRanking, combatPowerRanking));
		}

		return responses;
	}
}
