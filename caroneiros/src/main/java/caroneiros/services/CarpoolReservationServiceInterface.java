package caroneiros.services;

import caroneiros.domain.models.CarpoolReservation;
import caroneiros.dtos.ReservationRequestDTO;

public interface CarpoolReservationServiceInterface {
    CarpoolReservation reserveCarpool(ReservationRequestDTO reservation);
    CarpoolReservation findCarpoolReservationById(Long carpoolReservationId);
    void cancelCarpool(Long carpoolId , Long carpoolReservationId);
    boolean isCarpoolFull(Long carpoolId);
}
