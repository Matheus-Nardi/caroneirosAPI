package caroneiros.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import caroneiros.domain.models.CarpoolReservation;

public interface CarpoolReservationRepository extends JpaRepository<CarpoolReservation,Long>{
    List<CarpoolReservation> findAllByCarpoolId(Long carpoolId);

}
