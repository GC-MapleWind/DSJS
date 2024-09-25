package site.dpbr.dsjs.domain.character.presentation.dto.response;

import site.dpbr.dsjs.domain.character.domain.Character;

public record SearchCharacterInfoResponse(
	String name,
	String gender,
	String world,
	String job,
	Integer level,
	Integer unionLevel,
	Long combatPower,
	String characterImage,
	Integer charactersCount,
	Integer sameWorldCount,
	Integer sameJobCount,
	Double levelRanking,
	Double unionRanking,
	Double combatPowerRanking

) {
	public static SearchCharacterInfoResponse of(Character character, Integer charactersCount, Integer sameWorldCount,
		Integer sameJobCount, Double levelRanking, Double unionRanking, Double combatPowerRanking) {
		return new SearchCharacterInfoResponse(
			character.getName(),
			character.getGender(),
			character.getWorld(),
			character.getJob(),
			character.getLevel(),
			character.getUnionLevel(),
			character.getCombatPower(),
			character.getCharacterImage(),
			charactersCount,
			sameWorldCount,
			sameJobCount,
			levelRanking,
			unionRanking,
			combatPowerRanking);
	}
}
