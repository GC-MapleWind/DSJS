package site.dpbr.dsjs.domain.character.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CharacterStatInfoResponse(
        String date,

        @JsonProperty("character_class")
        String characterClass,

        @JsonProperty("final_stat")
        List<FinalStat> finalStats
) {
    public record FinalStat(
            @JsonProperty("stat_name")
            String statName,

            @JsonProperty("stat_value")
            String statValue
    ) {
    }
}
