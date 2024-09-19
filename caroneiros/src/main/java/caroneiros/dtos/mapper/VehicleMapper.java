package caroneiros.dtos.mapper;

import java.util.List;
import java.util.stream.Collectors;

import caroneiros.domain.models.Vehicle;
import caroneiros.dtos.vehicle.VehiclesResponseDTO;

public class VehicleMapper {

    public static VehiclesResponseDTO toVehiclesResponseDTO(Vehicle vehicle) {
        return new VehiclesResponseDTO(
                vehicle.getId(),
                vehicle.getDriver().getName(),
                vehicle.getLicensePlate(),
                vehicle.getModel(),
                vehicle.getColor());
    }

    public static List<VehiclesResponseDTO> toVehiclesResponseDTO(List<Vehicle> vehicles) {
        return vehicles.stream()
                .map(vehicle -> VehicleMapper.toVehiclesResponseDTO(vehicle))
                .collect(Collectors.toList());
    }
}
