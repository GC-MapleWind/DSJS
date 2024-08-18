package site.dpbr.dsjs.domain.character.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CharacterUnionInfoResponse(
        String date,

        @JsonProperty("union_level")
        Integer unionLevel,

        @JsonProperty("union_grade")
        String unionGrade,

        @JsonProperty("union_artifact_level")
        Integer unionArtifactLevel,

        @JsonProperty("union_artifact_exp")
        Integer unionArtifactExp,

        @JsonProperty("union_artifact_point")
        Integer unionArtifactPoint
) {
}
