package caroneiros.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record CarpoolResponseDTO(String driverName,
        String vehicleModel,
        String vehicleLisencePlate,
        String vehicleColor,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime estimatedDeparture,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime estimatedArrival,
        Double estimadedPrice,
        Integer seatsAvailable) {

}
