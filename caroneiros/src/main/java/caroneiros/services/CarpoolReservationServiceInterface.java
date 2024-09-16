package caroneiros.services;

import caroneiros.domain.models.CarpoolReservation;
import caroneiros.dtos.ReservationRequestDTO;
import caroneiros.dtos.ReservationResponseDTO;

public interface CarpoolReservationServiceInterface {
    ReservationResponseDTO reserveCarpool(Long passengerId, Long carpoolId , ReservationRequestDTO dto);
    CarpoolReservation findCarpoolReservationById(Long carpoolReservationId);
    void cancelCarpool(Long carpoolReservationId);
    boolean isCarpoolFull(Long carpoolId);
}
