package site.dpbr.dsjs.domain.character.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Builder
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

    @Column
    Integer level;

    @Column
    Integer union;

    @Column
    Integer stat;

    @Column
    String gender;
}
