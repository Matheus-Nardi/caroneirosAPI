package caroneiros.domain.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import caroneiros.domain.models.Carpool;

public interface CarpoolRepository extends JpaRepository<Carpool, Long> {

    @Query("select c from Carpool c WHERE c.seatsAvailable  > 0")
    List<Carpool> findAvailableCarpools();

    @Query("select c from Carpool c WHERE CAST(c.estimatedDeparture AS LocalDate) = current_date AND c.seatsAvailable  > 0 ")
    List<Carpool> findCarpoolsForToday();

    @Query("select c from Carpool c where CAST(c.estimatedDeparture AS LocalDate) = :date AND c.seatsAvailable  > 0")
    List<Carpool> findCarpoolsByDate(@Param("date") LocalDate date);

}
