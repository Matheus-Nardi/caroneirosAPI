package caroneiros.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import caroneiros.domain.models.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);
}
