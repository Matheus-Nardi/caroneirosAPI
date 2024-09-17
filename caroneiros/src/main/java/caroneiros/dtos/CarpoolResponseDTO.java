package caroneiros.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record CarpoolResponseDTO(String driverName,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime estimatedDeparture,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime estimatedArrival,
        Double estimadedPrice,
        String departureCity,
        String arrivalCity,
        Integer seatsAvailable) {

}
