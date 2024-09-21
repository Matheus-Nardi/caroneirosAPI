package caroneiros.services.reservation;

import java.util.List;

import caroneiros.dtos.reservation.ReservationRequestDTO;
import caroneiros.dtos.reservation.ReservationResponseDTO;

public interface CarpoolReservationService {
    ReservationResponseDTO reserveCarpool(Long passengerId, Long carpoolId , ReservationRequestDTO dto);
    ReservationResponseDTO findCarpoolReservationById(Long carpoolReservationId);
    List<ReservationResponseDTO> findReservationsByCarpoolId(Long carpoolId);
    void cancelReserveCarpool(Long carpoolReservationId);
}
