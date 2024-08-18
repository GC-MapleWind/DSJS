package site.dpbr.dsjs.domain.character.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CharacterBasicInfoResponse(
        String date,

        @JsonProperty("character_name")
        String characterName,

        @JsonProperty("world_name")
        String worldName,

        @JsonProperty("character_gender")
        String characterGender,

        @JsonProperty("character_class")
        String characterClass,

        @JsonProperty("character_class_level")
        String characterClassLevel,

        @JsonProperty("character_level")
        Integer characterLevel,

        @JsonProperty("character_exp")
        Long characterExp,

        @JsonProperty("character_exp_rate")
        String characterExpRate,

        @JsonProperty("character_guild_name")
        String characterGuildName,

        @JsonProperty("character_image")
        String characterImage,

        @JsonProperty("character_date_create")
        String characterDateCreate,

        @JsonProperty("access_flag")
        String accessFlag,

        @JsonProperty("liberation_quest_clear_flag")
        String liberationQuestClearFlag
) {
}
