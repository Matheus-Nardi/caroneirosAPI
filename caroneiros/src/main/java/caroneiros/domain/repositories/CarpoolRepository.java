package caroneiros.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import caroneiros.domain.models.Carpool;

public interface CarpoolRepository extends JpaRepository<Carpool,Long> {

}
