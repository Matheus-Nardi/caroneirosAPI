package caroneiros.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Carpool;
import caroneiros.domain.repositories.CarpoolRepository;
import caroneiros.dtos.CarpoolRequestDTO;
import caroneiros.infra.exceptions.NotFoundException;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CarpoolService implements CarpoolServiceInterface {

    @Autowired
    private CarpoolRepository carpoolRepository;

    @Autowired
    private AppUserService userService;

    @Autowired

    @Override
    public Carpool saveCarpool(CarpoolRequestDTO carpoolDTO) {
        AppUser driver = userService.findUserById(carpoolDTO.driverId());
        Carpool carpool = Carpool.builder()
                .driver(driver)
                .estimatedDeparture(carpoolDTO.estimatedDeparture())
                .estimatedArrival(carpoolDTO.estimatedDeparture().plusHours(1L))
                .estimadedPrice(carpoolDTO.estimadedPrice())
                .seatsAvailable(carpoolDTO.seatsAvailable())
                .build();

        return carpoolRepository.save(carpool);
    }

    @Override
    public Carpool findCarpoolById(Long id) {
        log.info("Buscando por carona de id [{}]", id);
        return carpoolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Carona não encontrada!"));
    }

    @Override
    public void deleteCarpoolById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCarpoolById'");
    }

    @Override
    public Carpool uptadeCarpool(Long id, Carpool carpoolToUpdate) {
        Carpool carpoolFromDB = findCarpoolById(id);

        int reservedSeats = carpoolFromDB.getReservations().size();
        if (carpoolToUpdate.getSeatsAvailable() < reservedSeats) {
            throw new IllegalArgumentException(
                    "A quantidade de assentos disponíveis não pode ser menor que o número de reservas confirmadas.");
        }
        carpoolFromDB.setDriver(carpoolToUpdate.getDriver());
        carpoolFromDB.setEstimatedDeparture(carpoolToUpdate.getEstimatedDeparture());
        carpoolFromDB.setEstimatedArrival(carpoolToUpdate.getEstimatedArrival());
        carpoolFromDB.setEstimadedPrice(carpoolToUpdate.getEstimadedPrice());
        carpoolFromDB.setSeatsAvailable(carpoolToUpdate.getSeatsAvailable());

        return carpoolRepository.save(carpoolFromDB);
    }

    @Override
    public List<Carpool> findAvailableCarpools() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAvailableCarpools'");
    }

    @Override
    public boolean isDriverAvailable(Long driverId, LocalDateTime departureTime) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isDriverAvailable'");
    }

    @Override
    public void completeCarpool(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'completeCarpool'");
    }
}
