package caroneiros.dtos.mapper;

import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Carpool;
import caroneiros.domain.models.Vehicle;
import caroneiros.dtos.CarpoolRequestDTO;
import caroneiros.dtos.CarpoolResponseDTO;

public class CarpoolMapper {

    public static CarpoolResponseDTO toCarpoolResponseDTO(Carpool carpool, Vehicle vehicle) {
        return new CarpoolResponseDTO(carpool.getDriver().getName(),
                vehicle.getModel(),
                vehicle.getLicensePlate(),
                vehicle.getColor(),
                carpool.getEstimatedDeparture(),
                carpool.getEstimadedPrice(),
                carpool.getSeatsAvailable());
    }

    public static Carpool toEntity(CarpoolRequestDTO carpoolDTO, AppUser driver) {
        return Carpool.builder()
                .driver(driver)
                .estimatedDeparture(carpoolDTO.estimatedDeparture())
                .estimatedArrival(carpoolDTO.estimatedDeparture().plusHours(1L))
                .estimadedPrice(carpoolDTO.estimadedPrice())
                .seatsAvailable(carpoolDTO.seatsAvailable())
                .build();
    }
}
