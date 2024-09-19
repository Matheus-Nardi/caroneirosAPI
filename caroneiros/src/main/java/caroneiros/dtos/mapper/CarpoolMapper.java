package caroneiros.dtos.mapper;

import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Carpool;
import caroneiros.domain.models.City;
import caroneiros.dtos.carpool.CarpoolRequestDTO;
import caroneiros.dtos.carpool.CarpoolResponseDTO;

public class CarpoolMapper {

    public static CarpoolResponseDTO toCarpoolResponseDTO(Carpool carpool) {
        return new CarpoolResponseDTO(carpool.getId(), carpool.getDriver().getName(),
                carpool.getEstimatedDeparture(),
                carpool.getEstimatedArrival(),
                carpool.getEstimatedPrice(),
                carpool.getDepartureCity().name,
                carpool.getArrivalCity().name,
                carpool.getSeatsAvailable());
    }

    public static Carpool toEntity(CarpoolRequestDTO carpoolDTO, AppUser driver) {
        City departureCity = carpoolDTO.departureCity();
        City arrivalCity = definerCity(departureCity);
        return Carpool.builder()
                .driver(driver)
                .departureCity(departureCity)
                .arrivalCity(arrivalCity)
                .estimatedDeparture(carpoolDTO.estimatedDeparture())
                .estimatedArrival(carpoolDTO.estimatedDeparture().plusHours(1L))
                .estimatedPrice(carpoolDTO.estimadedPrice())
                .seatsAvailable(carpoolDTO.seatsAvailable())
                .build();
    }

    private static City definerCity(City departureCity) {
        if (departureCity == City.PALMAS) {
            return City.PORTO_NACIONAL;
        } else {
            return City.PALMAS;
        }
    }
}
