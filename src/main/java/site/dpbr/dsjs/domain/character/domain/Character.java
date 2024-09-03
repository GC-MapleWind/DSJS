package site.dpbr.dsjs.domain.character.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.dpbr.dsjs.domain.character.presentation.dto.response.CharacterBasicInfoResponse;
import site.dpbr.dsjs.domain.character.presentation.dto.response.CharacterMuLungInfoResponse;
import site.dpbr.dsjs.domain.character.presentation.dto.response.CharacterStatInfoResponse;
import site.dpbr.dsjs.domain.character.presentation.dto.response.CharacterUnionInfoResponse;

import java.util.Optional;

@Getter
@Entity(name = "characters")
@NoArgsConstructor
public class Character {

    @Id
    @Column(nullable = false)
    String ocid;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String gender;

    @Column(nullable = false)
    String world;

    @Column(nullable = false)
    String job;

    @Column(name = "character_level", nullable = false)
    Integer level;

    @Column(nullable = false)
    Integer unionLevel;

    @Column(nullable = false)
    Integer unionArtifactLevel;

    @Column(nullable = false)
    Long combatPower;

    @Column(nullable = false)
    Integer muLungFloor;

    @Column(length = 500, nullable = false)
    String characterImage;

    private static final int INVALID_INT_VALUE = -1;
    private static final long INVALID_LONG_VALUE = -1L;

    public static Character from(String ocid, String name, CharacterBasicInfoResponse characterBasicInfoResponse, CharacterUnionInfoResponse characterUnionInfoResponse,
                                 CharacterStatInfoResponse characterStatInfoResponse, CharacterMuLungInfoResponse characterMuLungInfoResponse) {
        return Character.builder()
                .ocid(ocid)
                .name(name)
                .gender(characterBasicInfoResponse.characterGender())
                .world(characterBasicInfoResponse.worldName())
                .job(characterBasicInfoResponse.characterClass())
                .level(characterBasicInfoResponse.characterLevel())
                .unionLevel(Optional.ofNullable(characterUnionInfoResponse.unionLevel()).orElse(INVALID_INT_VALUE))
                .unionArtifactLevel(Optional.ofNullable(characterUnionInfoResponse.unionArtifactLevel()).orElse(INVALID_INT_VALUE))
                .combatPower(characterStatInfoResponse.finalStats().stream()
                        .filter(stat -> stat.statName().equals("전투력"))
                        .map(stat -> Long.parseLong(stat.statValue()))
                        .findFirst()
                        .orElse(INVALID_LONG_VALUE))
                .muLungFloor(Optional.ofNullable(characterMuLungInfoResponse.dojangBestFloor()).orElse(INVALID_INT_VALUE))
                .characterImage(characterBasicInfoResponse.characterImage())
                .build();
    }

    @Builder
    public Character(String ocid, String name, String gender, String world, String job, Integer level, Integer unionLevel,
                     Integer unionArtifactLevel, Long combatPower, Integer muLungFloor, String characterImage) {
        this.ocid = ocid;
        this.name = name;
        this.gender = gender;
        this.world = world;
        this.job = job;
        this.level = level;
        this.unionLevel = unionLevel;
        this.unionArtifactLevel = unionArtifactLevel;
        this.combatPower = combatPower;
        this.muLungFloor = muLungFloor;
        this.characterImage = characterImage;
    }
}