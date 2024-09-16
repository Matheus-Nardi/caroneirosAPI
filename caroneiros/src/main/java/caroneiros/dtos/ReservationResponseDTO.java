package caroneiros.dtos;

public record ReservationResponseDTO(
        String driverName,
        String passengerName,
        Integer desiredSeats) {

}
