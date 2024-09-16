package site.dpbr.dsjs.domain.character.presentation.dto.response;

import java.util.Optional;

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
    private static final int INVALID_INT_VALUE = -1;

    public static CharacterInfoResponse of(CharacterBasicInfoResponse basicInfo, CharacterUnionInfoResponse unionInfo,
                                           CharacterMuLungInfoResponse muLungInfo, Long maxCombatPower) {
        return new CharacterInfoResponse(
                basicInfo.characterGender(),
                basicInfo.worldName(),
                basicInfo.characterClass(),
                basicInfo.characterLevel(),
                Optional.ofNullable(unionInfo.unionLevel()).orElse(INVALID_INT_VALUE),
                Optional.ofNullable(unionInfo.unionArtifactLevel()).orElse(INVALID_INT_VALUE),
                maxCombatPower,
                muLungInfo.dojangBestFloor(),
                basicInfo.characterImage());
    }
}
