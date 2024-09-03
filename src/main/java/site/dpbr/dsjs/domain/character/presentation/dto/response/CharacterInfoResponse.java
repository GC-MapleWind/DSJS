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
    private static final long INVALID_LONG_VALUE = -1L;

    public static CharacterInfoResponse of(CharacterBasicInfoResponse basicInfo, CharacterUnionInfoResponse unionInfo,
                                           CharacterStatInfoResponse statInfo, CharacterMuLungInfoResponse muLungInfo) {
        return new CharacterInfoResponse(
                basicInfo.characterGender(),
                basicInfo.worldName(),
                basicInfo.characterClass(),
                basicInfo.characterLevel(),
                Optional.ofNullable(unionInfo.unionLevel()).orElse(INVALID_INT_VALUE),
                Optional.ofNullable(unionInfo.unionArtifactLevel()).orElse(INVALID_INT_VALUE),
                statInfo.finalStats().stream()
                        .filter(stat -> stat.statName().equals("전투력"))
                        .map(stat -> Long.parseLong(stat.statValue()))
                        .findFirst()
                        .orElse(INVALID_LONG_VALUE),
                muLungInfo.dojangBestFloor(),
                basicInfo.characterImage());
    }
}
