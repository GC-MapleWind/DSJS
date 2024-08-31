package site.dpbr.dsjs.domain.character.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dpbr.dsjs.domain.character.domain.Character;

import java.util.Optional;
import java.util.UUID;

public interface CharacterRepository extends JpaRepository<Character, UUID> {

    Optional<Character> findByName(String characterName);
}
