package caroneiros.services.reservation;

import caroneiros.dtos.reservation.ReservationRequestDTO;
import caroneiros.dtos.reservation.ReservationResponseDTO;

public interface CarpoolReservationServiceInterface {
    ReservationResponseDTO reserveCarpool(Long passengerId, Long carpoolId , ReservationRequestDTO dto);
    ReservationResponseDTO findCarpoolReservationById(Long carpoolReservationId);
    void cancelCarpool(Long carpoolReservationId);
}
