package caroneiros.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import caroneiros.domain.models.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select avg(r.score) from Review r where r.ratedDriver.id = :driverId  ")
    Double mediaDriverScore(@Param("driverId") Long driverId);
}
