package caroneiros.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Carpool;
import caroneiros.domain.models.Vehicle;
import caroneiros.domain.repositories.CarpoolRepository;
import caroneiros.dtos.CarpoolRequestDTO;
import caroneiros.dtos.CarpoolResponseDTO;
import caroneiros.dtos.mapper.CarpoolMapper;
import caroneiros.infra.exceptions.DontDriverException;
import caroneiros.infra.exceptions.NoVehiclesException;
import caroneiros.infra.exceptions.NotFoundException;
import caroneiros.infra.exceptions.VehicleNotOwnedException;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CarpoolService implements CarpoolServiceInterface {

    @Autowired
    private CarpoolRepository carpoolRepository;

    @Autowired
    private AppUserService userService;

    @Autowired
    private VehicleService vehicleService;

    @Override
    public CarpoolResponseDTO saveCarpool(CarpoolRequestDTO carpoolDTO) {
        AppUser driver = userService.findUserById(carpoolDTO.driverId());

        validateDriver(driver);

        Vehicle vehicle = getVehicleOwnership(carpoolDTO, driver);

        Carpool carpool = CarpoolMapper.toEntity(carpoolDTO, driver);

        carpoolRepository.save(carpool);
        return CarpoolMapper.toCarpoolResponseDTO(carpool, vehicle);
    }

    private Vehicle getVehicleOwnership(CarpoolRequestDTO carpoolDTO, AppUser driver) {
        Vehicle vehicle = vehicleService.getVehicleByIdOrThrow(carpoolDTO.vehicleId());
        if (!driver.getVehicles().contains(vehicle)) {
            throw new VehicleNotOwnedException("O véiculo não pertence ao usuário informado");
        }
        return vehicle;
    }

    private void validateDriver(AppUser driver) {
        if (!driver.isDriver())
            throw new DontDriverException("O usuário fornecido não é um motorista");

        if (driver.getVehicles().isEmpty())
            throw new NoVehiclesException("O usuário informado não possui veículos cadastrados");
    }

    @Override
    public Carpool findCarpoolById(Long id) {
        log.info("Buscando por carona de id [{}]", id);
        return carpoolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Carona não encontrada!"));
    }

    @Override
    public void deleteCarpoolById(Long id) {
        log.info("Deletando carona de id [{}]", id);
        Carpool carpool = carpoolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Carona não encontrada!"));
        carpoolRepository.delete(carpool);

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
        log.info("Buscando por caronas que possuem assentos disponíveis");
        return carpoolRepository.findAvailableCarpools();
    }
}
