package caroneiros.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import caroneiros.domain.models.Review;

public interface ReviewRepository extends JpaRepository<Review,Long> {

}
