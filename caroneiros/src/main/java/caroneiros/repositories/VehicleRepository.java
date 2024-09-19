package caroneiros.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import caroneiros.domain.models.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle,Long> {

    List<Vehicle> findByDriver_Id(Long userId);
}
