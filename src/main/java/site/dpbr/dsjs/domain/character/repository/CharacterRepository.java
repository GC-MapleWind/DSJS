package site.dpbr.dsjs.domain.character.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dpbr.dsjs.domain.character.Character;

import java.util.UUID;

public interface CharacterRepository extends JpaRepository<Character, UUID> {

}
