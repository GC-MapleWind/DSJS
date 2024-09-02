package site.dpbr.dsjs.domain.admin.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.dpbr.dsjs.domain.admin.domain.Admin;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findByUsername(String username);
}
