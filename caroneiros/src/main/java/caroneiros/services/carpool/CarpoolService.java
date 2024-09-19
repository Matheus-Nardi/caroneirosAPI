package caroneiros.services.carpool;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Carpool;
import caroneiros.domain.models.Vehicle;
import caroneiros.dtos.carpool.CarpoolRequestDTO;
import caroneiros.dtos.carpool.CarpoolResponseDTO;
import caroneiros.dtos.carpool.CarpoolUpdateRequestDTO;
import caroneiros.dtos.mapper.CarpoolMapper;
import caroneiros.infra.exceptions.DontDriverException;
import caroneiros.infra.exceptions.InvalidOperationException;
import caroneiros.infra.exceptions.NoVehiclesException;
import caroneiros.infra.exceptions.NotFoundException;
import caroneiros.infra.exceptions.VehicleNotOwnedException;
import caroneiros.repositories.CarpoolRepository;
import caroneiros.services.appuser.AppUserService;
import caroneiros.services.vehicle.VehicleService;
import jakarta.transaction.Transactional;
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
    public CarpoolResponseDTO saveCarpool(Long driverId, Long vehicleId, CarpoolRequestDTO carpoolDTO) {

        AppUser driver = userService.findUserById(driverId);

        validateDriver(driver);

        valideVehicleOwnership(vehicleId, driver);

        Carpool carpool = CarpoolMapper.toEntity(carpoolDTO, driver);
        this.saveCarpool(carpool);
        return CarpoolMapper.toCarpoolResponseDTO(carpool);
    }

    private void valideVehicleOwnership(Long vehicleId, AppUser driver) {
        Vehicle vehicle = vehicleService.getVehicleByIdOrThrow(vehicleId);
        if (!driver.getVehicles().contains(vehicle)) {
            throw new VehicleNotOwnedException("O véiculo não pertence ao usuário informado");
        }
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
        return getCarpoolByIdOrThrow(id);
    }

    private Carpool getCarpoolByIdOrThrow(Long id) {
        return carpoolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Carona não encontrada!"));
    }

    @Override
    @Transactional
    public void deleteCarpoolById(Long id) {
        log.info("Deletando carona de id [{}]", id);
        Carpool carpool = getCarpoolByIdOrThrow(id);
        if (!carpool.getReservations().isEmpty())
            throw new InvalidOperationException("Sua carona já possui reservas. Não é possivel deleta-la");
        carpoolRepository.delete(carpool);

    }

    @Override
    @Transactional
    public Carpool uptadeCarpool(Long id, Carpool carpoolToUpdate) {
        Carpool carpoolFromDB = findCarpoolById(id);
        carpoolFromDB.setDriver(carpoolToUpdate.getDriver());
        carpoolFromDB.setEstimatedArrival(carpoolToUpdate.getEstimatedArrival());
        carpoolFromDB.setEstimatedDeparture(carpoolToUpdate.getEstimatedDeparture());
        carpoolFromDB.setEstimatedPrice(carpoolToUpdate.getEstimatedPrice());
        carpoolFromDB.setSeatsAvailable(carpoolToUpdate.getSeatsAvailable());

        return carpoolRepository.save(carpoolFromDB);
    }

  

    @Override
    @Transactional
    public Carpool saveCarpool(Carpool carpool) {
        log.info("Salvando carona [{}]", carpool.getId());
        return carpoolRepository.save(carpool);
    }

    @Override
    public CarpoolResponseDTO updateCarpool(Long id, CarpoolUpdateRequestDTO carpoolUpdateRequestDTO) {
        Carpool carpoolFromDB = findCarpoolById(id);

        if (carpoolFromDB.getReservations().isEmpty()) {
            throw new InvalidOperationException("Sua carona já possui reservas. Não é possivel atualiza-la");
        }
        carpoolFromDB.setEstimatedDeparture(carpoolUpdateRequestDTO.estimatedDeparture());
        carpoolFromDB.setEstimatedPrice(carpoolUpdateRequestDTO.estimatedPrice());
        carpoolFromDB.setSeatsAvailable(carpoolUpdateRequestDTO.seatsAvailable());

        Carpool carpoolUpdated = carpoolRepository.save(carpoolFromDB);
        return CarpoolMapper.toCarpoolResponseDTO(carpoolUpdated);

    }

    @Override
    public List<CarpoolResponseDTO> filterCarpools(Carpool entityFilter) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(StringMatcher.CONTAINING);
        Example<Carpool> carpoolExample = Example.of(entityFilter, exampleMatcher);
        List<Carpool> carpools = carpoolRepository.findAll(carpoolExample);
        return carpools.stream().map(carpool -> CarpoolMapper.toCarpoolResponseDTO(carpool))
                .collect(Collectors.toList());

    }

    @Override
    public List<CarpoolResponseDTO> findCarpoolsByDate(LocalDate date) {
        log.info("Buscando caronas para a data de [{}]", date);
        return carpoolRepository.findCarpoolsByDate(date).stream()
                .map(carpool -> CarpoolMapper.toCarpoolResponseDTO(carpool)).collect(Collectors.toList());
    }
}
