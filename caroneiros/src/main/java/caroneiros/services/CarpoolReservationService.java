package caroneiros.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Carpool;
import caroneiros.domain.models.CarpoolReservation;
import caroneiros.domain.models.CarpoolStatus;
import caroneiros.domain.repositories.CarpoolReservationRepository;
import caroneiros.dtos.ReservationRequestDTO;
import caroneiros.dtos.ReservationResponseDTO;
import caroneiros.dtos.mapper.CarpoolReserrvationMapper;
import caroneiros.infra.exceptions.NoSeatsAvaliableException;
import caroneiros.infra.exceptions.NotFoundException;
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
    public ReservationResponseDTO reserveCarpool(Long passengerId, Long carpoolId, ReservationRequestDTO dto) {
        log.info("Realizando a reserva da carona [{}]", carpoolId);
        AppUser passenger = appUserService.findUserById(passengerId);
        Carpool carpool = valideCarpoolOrThrow(carpoolId, dto.desiredSeats());

        if (carpool.getDriver().getId().equals(passengerId))
            throw new IllegalArgumentException("O motorista não pode ser reservar uma carona!");
        carpool.setSeatsAvailable(carpool.getSeatsAvailable() - dto.desiredSeats());
        CarpoolReservation carpoolReservation = CarpoolReserrvationMapper.toEntity(passenger, carpool,
                dto.desiredSeats());

        carpool.addReservation(carpoolReservation);
        carpoolReservationRepository.save(carpoolReservation);
        carpoolService.uptadeCarpool(carpool.getId(), carpool);
        return CarpoolReserrvationMapper.toReservationResponseDTO(carpoolReservation, carpool);
    }

    private Carpool valideCarpoolOrThrow(Long carpoolId, Integer desiredSeats) {
        Carpool carpool = carpoolService.findCarpoolById(carpoolId);

        if (!carpool.hasAvailableSeats())
            throw new NoSeatsAvaliableException("Não há assentos disponíveis");

        if (desiredSeats > carpool.getSeatsAvailable())
            throw new NoSeatsAvaliableException("A quantidade desejada é maior que a disponível");
        return carpool;
    }

    @Override
    @Transactional
    public void cancelCarpool(Long carpoolReservationId) {

        CarpoolReservation reservation = findCarpoolReservationById(carpoolReservationId);
        Carpool carpool = reservation.getCarpool();

        if (reservation.getStatus() == CarpoolStatus.CANCELLED) {
            throw new IllegalStateException("Reservation is already cancelled.");
        }
        reservation.setStatus(CarpoolStatus.CANCELLED);
        carpool.setSeatsAvailable(carpool.getSeatsAvailable() + reservation.getSeatsReserved());
        carpool.getReservations().remove(reservation);
        carpoolService.saveCarpool(carpool);
        carpoolReservationRepository.save(reservation);

    }

    @Override
    public boolean isCarpoolFull(Long carpoolId) {
        Carpool carpool = carpoolService.findCarpoolById(carpoolId);
        return carpool.getSeatsAvailable() <= 0;
    }

    @Override
    public CarpoolReservation findCarpoolReservationById(Long carpoolReservationId) {
        log.info("Buscando reserva de id [{}] ", carpoolReservationId);
        return carpoolReservationRepository.findById(carpoolReservationId)
                .orElseThrow(() -> new NotFoundException("Reserva de carona não encontrada!"));
    }

}
