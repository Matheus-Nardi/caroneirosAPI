package caroneiros.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Vehicle;
import caroneiros.domain.repositories.VehicleRepository;
import caroneiros.infra.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public void addVehicleForUser(AppUser user, Vehicle vehicle) {
        log.info("Adicionando veículo [{}] para {[]}", vehicle.getModel(), user.getName());
        vehicle.setDriver(user);
        vehicleRepository.save(vehicle);
    }

    public Vehicle findVehicleById(Long id) {
        log.info("Buscando veículo de id [{}]", id);
        return vehicleRepository.findById(id).orElseThrow(() -> new NotFoundException("Véiculo não encontrado"));
    }

    @Transactional
    public void deleteVehicleById(Long id) {
        log.info("Deletando veículo de id [{}]", id);
        Vehicle vehicleToDelete = vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Véiculo não encontrado"));
        vehicleRepository.delete(vehicleToDelete);
    }

    // Preciso fazer o teste
    public Vehicle updateVehicle(Long id , Vehicle vehicleToUpdate){
        log.info("Atualizando veículo de id [{}]" , id);
        Vehicle vehicleFromDB = findVehicleById(id);

        if(vehicleToUpdate.getColor() != null){
            vehicleFromDB.setColor(vehicleToUpdate.getColor());
        }
        if(vehicleToUpdate.getModel() != null){
            vehicleFromDB.setModel((vehicleToUpdate.getModel()));
        }

        return vehicleRepository.save(vehicleFromDB);
        
    }

}
