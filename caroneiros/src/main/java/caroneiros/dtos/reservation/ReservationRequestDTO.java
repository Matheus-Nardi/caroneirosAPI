package caroneiros.dtos.reservation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReservationRequestDTO(
     @NotNull(message = "{field.seatsAvailable.mandatory}") 
     @Min(1) @Max(4)
    Integer desiredSeats ) {

}
