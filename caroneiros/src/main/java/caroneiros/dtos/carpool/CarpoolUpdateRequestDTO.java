package caroneiros.dtos.carpool;

import java.time.LocalDateTime;

public record CarpoolUpdateRequestDTO(LocalDateTime estimatedDeparture , Double estimatedPrice , Integer seatsAvailable) {

}
