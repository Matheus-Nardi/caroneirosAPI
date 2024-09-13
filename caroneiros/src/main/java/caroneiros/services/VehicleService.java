package caroneiros.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Vehicle;
import caroneiros.domain.repositories.VehicleRepository;
import caroneiros.dtos.VehiclesResponseDTO;
import caroneiros.dtos.mapper.VehicleMapper;
import caroneiros.infra.exceptions.DontDriverException;
import caroneiros.infra.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private AppUserService appUserService;

    @Transactional
    public VehiclesResponseDTO registerVehicle(Long driverId, Vehicle vehicle) {
        log.info("Registrando veículo [{}] para o usúario [{}]", vehicle.getModel(),
                driverId);
        AppUser driver = appUserService.findUserById(driverId);

        if (!driver.isDriver()) {
            throw new DontDriverException("O usuário informado não é um motorista");
        }
        vehicle.setDriver(driver);
        vehicleRepository.save(vehicle);
        return VehicleMapper.toVehiclesResponseDTO(vehicle);
    }

    public List<VehiclesResponseDTO> findAllVehicleByUser(Long userId) {
        List<Vehicle> vehicles = vehicleRepository.findByDriver_Id(userId);
        return vehicles.stream()
                .map(VehicleMapper::toVehiclesResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteVehicleById(long userId, Long vehicleId) {
        log.info("Deletando veículo de id [{}] do usuario", vehicleId, userId);
        Vehicle vehicleFromDB = getVehicleByIdOrThrow(vehicleId);
        if (!vehicleFromDB.getDriver().getId().equals(userId)) {
            throw new NotFoundException("O veículo não pertence ao usuário informado");
        }
        vehicleRepository.delete(vehicleFromDB);
    }

    @Transactional
    public VehiclesResponseDTO updateVehicle(Long userId, Long vehicleId, Vehicle vehicleToUpdate) {
        log.info("Atualizando veículo de id [{}]", vehicleId);
        Vehicle vehicleFromDB = getVehicleByIdOrThrow(vehicleId);

        if (!vehicleFromDB.getDriver().getId().equals(userId)) {
            throw new NotFoundException("O veículo não pertence ao usuário informado");
        }

        if (vehicleToUpdate.getColor() != null) {
            vehicleFromDB.setColor(vehicleToUpdate.getColor());
        }
        if (vehicleToUpdate.getModel() != null) {
            vehicleFromDB.setModel((vehicleToUpdate.getModel()));
        }
        Vehicle vehicleUpdated = vehicleRepository.save(vehicleFromDB);
        return VehicleMapper.toVehiclesResponseDTO(vehicleUpdated);
    }

    private Vehicle getVehicleByIdOrThrow(Long vehicleId) {
        log.info("Busca por veículo de id [{}]", vehicleId);
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NotFoundException("Véiculo não encontrado"));
    }

}
