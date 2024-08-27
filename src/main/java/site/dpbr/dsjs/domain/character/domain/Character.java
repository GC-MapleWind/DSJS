package site.dpbr.dsjs.domain.character.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.dpbr.dsjs.domain.character.presentation.dto.response.CharacterBasicInfoResponse;
import site.dpbr.dsjs.domain.character.presentation.dto.response.CharacterMuLungInfoResponse;
import site.dpbr.dsjs.domain.character.presentation.dto.response.CharacterStatInfoResponse;
import site.dpbr.dsjs.domain.character.presentation.dto.response.CharacterUnionInfoResponse;

import java.util.UUID;

@Getter
@Entity(name = "characters")
@AllArgsConstructor
@NoArgsConstructor
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;

    @Column(nullable = false)
    String ocid;

    @Column(nullable = false)
    String name;

    @Column
    String world;

    @Column
    String job;

    @Column(name = "character_level")
    Integer level;

    @Column
    Integer unionLevel;

    @Column
    Integer unionArtifactLevel;

    @Column
    Long combatPower;

    @Column
    Integer muLungFloor;

    @Column
    String gender;

    @Column(length = 500)
    String characterImage;

    public static Character create(String ocid, String name) {
        return Character.builder()
                .ocid(ocid)
                .name(name)
                .build();
    }

    @Builder
    public Character(String ocid, String name) {
        this.ocid = ocid;
        this.name = name;
    }

    public void updateBasicInfo(CharacterBasicInfoResponse response) {
        this.world = response.worldName();
        this.job = response.characterClass();
        this.level = response.characterLevel();
        this.gender = response.characterGender();
        this.characterImage = response.characterImage();
    }

    public void updateUnionInfo(CharacterUnionInfoResponse response) {
        this.unionLevel = (response.unionLevel() != null) ? response.unionLevel() : -1;
        this.unionArtifactLevel = (response.unionArtifactLevel() != null) ? response.unionArtifactLevel() : -1;
    }

    public void updateStatInfo(CharacterStatInfoResponse response) {
        this.combatPower = response.finalStats().stream()
                .filter(stat -> stat.statName().equals("전투력"))
                .map(stat -> Long.parseLong(stat.statValue()))
                .findFirst()
                .orElse(-1L);
    }

    public void updateMuLungInfo(CharacterMuLungInfoResponse response) {
        this.muLungFloor = (response.dojangBestFloor() != null) ? response.dojangBestFloor() : -1;
    }
}
