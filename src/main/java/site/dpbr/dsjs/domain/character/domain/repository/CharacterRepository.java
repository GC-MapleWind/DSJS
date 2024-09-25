package site.dpbr.dsjs.domain.character.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import site.dpbr.dsjs.domain.character.domain.Character;

public interface CharacterRepository extends JpaRepository<Character, UUID> {

	Optional<Character> findByName(String characterName);

	List<Character> findAllByWorld(String world);

	List<Character> findAllByJob(String job);

	List<Character> findAllByOrderByCombatPowerDesc();

	List<Character> findAllByOrderByLevelDesc();

	List<Character> findAllByOrderByUnionLevelDesc();
}
