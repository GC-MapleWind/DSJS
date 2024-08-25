package site.dpbr.dsjs.domain.character.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CharacterMuLungInfoResponse(
        String date,

        @JsonProperty("character_class")
        String characterClass,

        @JsonProperty("world_name")
        String worldName,

        @JsonProperty("dojang_best_floor")
        Integer dojangBestFloor,

        @JsonProperty("date_dojang_record")
        String dateDojangRecord,

        @JsonProperty("dojang_best_time")
        Integer dojangBestTime
) {
}
