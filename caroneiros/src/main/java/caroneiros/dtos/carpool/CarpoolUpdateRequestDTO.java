package caroneiros.dtos.carpool;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record CarpoolUpdateRequestDTO( @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime estimatedDeparture , Double estimatedPrice , Integer seatsAvailable) {

}
