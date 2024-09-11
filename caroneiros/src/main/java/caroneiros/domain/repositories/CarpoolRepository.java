package caroneiros.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import caroneiros.domain.models.Carpool;

public interface CarpoolRepository extends JpaRepository<Carpool,Long> {

    @Query("select c from Carpool c WHERE c.seatsAvailable  > 0")
    List<Carpool> findAvailableCarpools();
}
