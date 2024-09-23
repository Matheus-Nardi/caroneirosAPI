package caroneiros.services.vehicle;

import java.util.List;

import caroneiros.domain.models.Vehicle;
import caroneiros.dtos.vehicle.VehicleDTO;
import caroneiros.dtos.vehicle.VehiclesResponseDTO;

public interface VehicleService {
    VehiclesResponseDTO registerVehicle(Long driverId, VehicleDTO dto);

    List<VehiclesResponseDTO> findAllVehicleByUser(Long userId);

    void deleteVehicleById(long userId, Long vehicleId);

    VehiclesResponseDTO updateVehicle(Long userId, Long vehicleId, VehicleDTO vehicleToUpdate);

    Vehicle getVehicleByIdOrThrow(Long vehicleId);
}
