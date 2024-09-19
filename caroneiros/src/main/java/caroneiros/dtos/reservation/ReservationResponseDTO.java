package caroneiros.dtos.reservation;

public record ReservationResponseDTO(
        Long id,
        Long carpoolReserved,
        String passengerName,
        Integer desiredSeats) {

}
