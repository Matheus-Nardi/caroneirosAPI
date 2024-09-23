package caroneiros.services.vehicle;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Vehicle;
import caroneiros.dtos.mapper.VehicleMapper;
import caroneiros.dtos.vehicle.VehicleDTO;
import caroneiros.dtos.vehicle.VehiclesResponseDTO;
import caroneiros.infra.exceptions.DontDriverException;
import caroneiros.infra.exceptions.NotFoundException;
import caroneiros.infra.exceptions.VehicleNotOwnedException;
import caroneiros.repositories.VehicleRepository;
import caroneiros.services.appuser.AppUserService;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private AppUserService appUserService;

    @Transactional
    @Override
    public VehiclesResponseDTO registerVehicle(Long driverId, VehicleDTO dto) {
        log.info("Registrando veículo [{}] para o usúario [{}]", dto.model(),
                driverId);
        AppUser driver = appUserService.findUserById(driverId);
        Vehicle vehicle = new Vehicle(dto);
        if (!driver.isDriver()) {
            throw new DontDriverException("O usuário informado não é um motorista");
        }
        vehicle.setDriver(driver);
        vehicleRepository.save(vehicle);
        return VehicleMapper.toVehiclesResponseDTO(vehicle);
    }

    @Override
    public List<VehiclesResponseDTO> findAllVehicleByUser(Long userId) {
        List<Vehicle> vehicles = vehicleRepository.findByDriver_Id(userId);
        return vehicles.stream()
                .map(VehicleMapper::toVehiclesResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteVehicleById(long userId, Long vehicleId) {
        log.info("Deletando veículo de id [{}] do usuario", vehicleId, userId);
        Vehicle vehicleFromDB = getVehicleByIdOrThrow(vehicleId);
        if (!vehicleFromDB.getDriver().getId().equals(userId)) {
            throw new VehicleNotOwnedException("O veículo não pertence ao usuário informado");
        }
        vehicleRepository.delete(vehicleFromDB);
    }

    @Transactional
    @Override
    public VehiclesResponseDTO updateVehicle(Long userId, Long vehicleId, VehicleDTO vehicleToUpdate) {
        log.info("Atualizando veículo de id [{}]", vehicleId);
        Vehicle vehicleFromDB = getVehicleByIdOrThrow(vehicleId);

        if (!vehicleFromDB.getDriver().getId().equals(userId)) {
            throw new VehicleNotOwnedException("O veículo não pertence ao usuário informado");
        }

        if (vehicleToUpdate.licensePlate() != null) {
            vehicleFromDB.setLicensePlate(vehicleToUpdate.licensePlate());
        }
        if (vehicleToUpdate.color() != null) {
            vehicleFromDB.setColor(vehicleToUpdate.color());
        }
        if (vehicleToUpdate.model() != null) {
            vehicleFromDB.setModel((vehicleToUpdate.model()));
        }
        Vehicle vehicleUpdated = vehicleRepository.save(vehicleFromDB);
        return VehicleMapper.toVehiclesResponseDTO(vehicleUpdated);
    }

    @Override
    public Vehicle getVehicleByIdOrThrow(Long vehicleId) {
        log.info("Busca por veículo de id [{}]", vehicleId);
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NotFoundException("Véiculo não encontrado"));
    }

}
