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
    String name;

    @Column(nullable = false)
    String world;

    @Column(nullable = false)
    String job;

    @Column(nullable = false)
    Integer level;

    @Column(nullable = false)
    Integer union;

    @Column(nullable = false)
    Integer stat;

    @Column(nullable = false)
    String gender;
}
