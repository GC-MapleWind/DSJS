package site.dpbr.dsjs.domain.character.presentation.dto.response;

public record CharacterInfoResponse(
        String gender,
        String world,
        String job,
        Integer level,
        Integer unionLevel,
        Integer unionArtifactLevel,
        Long combatPower,
        Integer muLungFloor,
        String characterImage
) {

    public static CharacterInfoResponse of(CharacterBasicInfoResponse basicInfo, CharacterUnionInfoResponse unionInfo,
                                           CharacterMuLungInfoResponse muLungInfo, Long maxCombatPower) {
        return new CharacterInfoResponse(
                basicInfo.characterGender(),
                basicInfo.worldName(),
                basicInfo.characterClass(),
                basicInfo.characterLevel(),
                unionInfo.unionLevel(),
                unionInfo.unionArtifactLevel(),
                maxCombatPower,
                muLungInfo.dojangBestFloor(),
                basicInfo.characterImage());
    }
}
