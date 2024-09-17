package caroneiros.dtos.mapper;

import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Carpool;
import caroneiros.domain.models.CarpoolReservation;
import caroneiros.domain.models.CarpoolStatus;
import caroneiros.dtos.ReservationResponseDTO;

public class CarpoolReserrvationMapper {

    public static CarpoolReservation toEntity(AppUser passenger, Carpool carpool, Integer desiredSeats) {
        return CarpoolReservation.builder()
                .passenger(passenger)
                .carpool(carpool)
                .seatsReserved(desiredSeats)
                .status(CarpoolStatus.CONFIRMED)
                .build();
    }

    public static ReservationResponseDTO toReservationResponseDTO(CarpoolReservation carpoolReservation,
            Carpool carpool) {
        return new ReservationResponseDTO(carpool.getId(), carpoolReservation.getPassenger().getName(),
                carpoolReservation.getSeatsReserved());
    }
}
