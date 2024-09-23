package caroneiros.dtos.carpool;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CarpoolRequestDTO(
                @NotEmpty(message = "The field departure city is mandatory") String departureCity,
                @JsonFormat(pattern = "dd/MM/yyyy HH:mm") @NotNull(message = "Set a estimated departure") LocalDateTime estimatedDeparture,
                @NotNull(message = "The field estimated price is mandatory") 
                Double estimadedPrice,
                @NotNull(message = "The filed seats is mandatory") @Min(0) @Max(4) Integer seatsAvailable) {

}
