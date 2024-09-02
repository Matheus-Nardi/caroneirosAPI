package caroneiros.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import caroneiros.domain.models.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {

}
