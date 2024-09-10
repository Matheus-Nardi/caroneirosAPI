package caroneiros.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Carpool;
import caroneiros.domain.models.CarpoolReservation;
import caroneiros.domain.models.CarpoolStatus;
import caroneiros.domain.repositories.CarpoolReservationRepository;
import caroneiros.dtos.ReservationRequestDTO;
import caroneiros.infra.exceptions.NoSeatsAvaliableException;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CarpoolReservationService implements CarpoolReservationServiceInterface {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private CarpoolService carpoolService;

    @Autowired
    private CarpoolReservationRepository carpoolReservationRepository;

    @Override
    public CarpoolReservation reserveCarpool(ReservationRequestDTO reservation) {
        log.info("Realizando a reserva da carona [{}]", reservation.carpoolId());
        AppUser passanger = appUserService.findUserById(reservation.passengerId());
        Carpool carpool = carpoolService.findCarpoolById(reservation.carpoolId());

        if (!carpool.hasAvailableSeats())
            throw new NoSeatsAvaliableException("Não há assentos disponíveis");

        if(reservation.desiredSeats() > carpool.getSeatsAvailable())
            throw new NoSeatsAvaliableException("A quantidade desejada é maior que a disponível");

        carpool.setSeatsAvailable(carpool.getSeatsAvailable() - reservation.desiredSeats());
        CarpoolReservation carpoolReservation = CarpoolReservation.builder()
                .passenger(passanger)
                .carpool(carpool)
                .status(CarpoolStatus.CONFIRMED)
                .build();

        carpool.addReservation(carpoolReservation);
        carpoolReservationRepository.save(carpoolReservation);
        carpoolService.uptadeCarpool(carpool.getId(), carpool);
        return carpoolReservation;
    }

    @Override
    public void cancelCarpool(Long carpoolId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancelCarpool'");
    }

    @Override
    public boolean isCarpoolFull(Long carpoolId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isCarpoolFull'");
    }

}
