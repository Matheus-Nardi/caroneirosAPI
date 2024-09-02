package caroneiros.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import caroneiros.domain.models.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

}
