package caroneiros.services.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Carpool;
import caroneiros.domain.models.CarpoolReservation;
import caroneiros.domain.models.CarpoolStatus;
import caroneiros.dtos.mapper.CarpoolReserrvationMapper;
import caroneiros.dtos.reservation.ReservationRequestDTO;
import caroneiros.dtos.reservation.ReservationResponseDTO;
import caroneiros.infra.exceptions.InvalidOperationException;
import caroneiros.infra.exceptions.NoSeatsAvaliableException;
import caroneiros.infra.exceptions.NotFoundException;
import caroneiros.repositories.CarpoolReservationRepository;
import caroneiros.services.appuser.AppUserService;
import caroneiros.services.carpool.CarpoolService;
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
            throw new InvalidOperationException("O motorista não pode ser reservar uma carona!");
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

        CarpoolReservation reservation = getReservationByIdOrThrow(carpoolReservationId);
        Carpool carpool = reservation.getCarpool();

        if (reservation.getStatus() == CarpoolStatus.CANCELLED) {
            throw new InvalidOperationException("A reserva já foi cancelada.");
        }
        reservation.setStatus(CarpoolStatus.CANCELLED);
        carpool.setSeatsAvailable(carpool.getSeatsAvailable() + reservation.getSeatsReserved());
        carpool.getReservations().remove(reservation);
        carpoolService.saveCarpool(carpool);
        carpoolReservationRepository.save(reservation);

    }

    @Override
    public ReservationResponseDTO findCarpoolReservationById(Long carpoolReservationId) {
        log.info("Buscando reserva de id [{}] ", carpoolReservationId);
        CarpoolReservation carpoolReservation = getReservationByIdOrThrow(carpoolReservationId);
        return CarpoolReserrvationMapper.toReservationResponseDTO(carpoolReservation, carpoolReservation.getCarpool());
    }

    private CarpoolReservation getReservationByIdOrThrow(Long id) {
        return carpoolReservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reserva de carona não encontrada!"));
    }

}
