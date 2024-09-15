package caroneiros.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record CarpoolRequestDTO(Long driverId, Long vehicleId,
                @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime estimatedDeparture,
                Double estimadedPrice,
                Integer seatsAvailable) {

}
