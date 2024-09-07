package caroneiros.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Vehicle;
import caroneiros.domain.repositories.VehicleRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public void addVehicleForUser(AppUser user, Vehicle vehicle){
        log.info("Adicionando veículo [{}] para {[]}" , vehicle.getModel() , user.getName());
        vehicle.setDriver(user);
        vehicleRepository.save(vehicle);
    }

    public void deleteVehicleById(Long id){
        log.info("Deletando veículo de id [{}]");
    }
}
