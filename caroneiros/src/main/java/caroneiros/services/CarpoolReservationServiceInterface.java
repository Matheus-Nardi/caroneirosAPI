package caroneiros.services;

import caroneiros.domain.models.CarpoolReservation;
import caroneiros.dtos.ReservationRequestDTO;

public interface CarpoolReservationServiceInterface {
    CarpoolReservation reserveCarpool(ReservationRequestDTO reservation);
    void cancelCarpool(Long carpoolId);
    boolean isCarpoolFull(Long carpoolId);
}
