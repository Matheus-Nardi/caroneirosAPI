package caroneiros.dtos.carpool;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CarpoolUpdateRequestDTO(
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm") @NotNull(message = "{field.estimatedDeparture.mandatory}") LocalDateTime estimatedDeparture,
        @NotNull(message = "{field.estimatedPrice.mandatory}") Double estimatedPrice,
        @NotNull(message = "{field.seatsAvailable.mandatory}") @Min(value = 0, message = "{field.seatsAvailable.min}") @Max(value = 4, message = "{field.seatsAvailable.max}") Integer seatsAvailable
) {
}
