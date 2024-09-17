package caroneiros.dtos;

public record ReservationResponseDTO(
        Long carpoolReserved,
        String passengerName,
        Integer desiredSeats) {

}
