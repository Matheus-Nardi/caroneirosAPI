package caroneiros.dtos.carpool;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CarpoolUpdateRequestDTO(
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm") @NotNull(message = "Set a estimated departure") LocalDateTime estimatedDeparture,
        @NotNull(message = "The field estimated price is mandatory") Double estimatedPrice,
        @NotNull(message = "The filed seats is mandatory") @Min(0) @Max(4) Integer seatsAvailable) {

}
