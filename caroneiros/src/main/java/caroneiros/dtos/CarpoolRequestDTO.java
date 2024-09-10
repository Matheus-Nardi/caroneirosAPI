package caroneiros.dtos;
import java.time.LocalDateTime;
public record CarpoolRequestDTO(Long driverId ,  LocalDateTime estimatedDeparture , Double estimadedPrice , Integer seatsAvailable) {

}
