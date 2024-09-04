package caroneiros.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import caroneiros.domain.models.CarpoolReservation;

public interface CarpoolReservationRepository extends JpaRepository<CarpoolReservation,Long>{

}
