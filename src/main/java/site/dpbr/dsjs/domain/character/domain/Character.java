package site.dpbr.dsjs.domain.character.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.dpbr.dsjs.domain.character.presentation.dto.response.CharacterInfoResponse;

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

    public static Character create(String ocid, String name, CharacterInfoResponse characterInfoResponse) {
        return Character.builder()
                .ocid(ocid)
                .name(name)
                .gender(characterInfoResponse.gender())
                .world(characterInfoResponse.world())
                .job(characterInfoResponse.job())
                .level(characterInfoResponse.level())
                .unionLevel(characterInfoResponse.unionLevel())
                .unionArtifactLevel(characterInfoResponse.unionArtifactLevel())
                .combatPower(characterInfoResponse.combatPower())
                .muLungFloor(characterInfoResponse.muLungFloor())
                .characterImage(characterInfoResponse.characterImage())
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