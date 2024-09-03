package site.dpbr.dsjs.domain.character.presentation.dto.response;

import site.dpbr.dsjs.domain.character.domain.Character;

public record SearchCharacterInfoResponse(
        String name,
        String gender,
        String world,
        String job,
        Integer level,
        Integer unionLevel,
        Integer unionArtifactLevel,
        Long combatPower,
        Integer muLungFloor,
        String characterImage,
        Double percentage
) {
    public static SearchCharacterInfoResponse from(Character character, Double percentage) {
        return new SearchCharacterInfoResponse(
                character.getName(),
                character.getGender(),
                character.getWorld(),
                character.getJob(),
                character.getLevel(),
                character.getUnionLevel(),
                character.getUnionArtifactLevel(),
                character.getCombatPower(),
                character.getMuLungFloor(),
                character.getCharacterImage(),
                percentage);
    }
}
