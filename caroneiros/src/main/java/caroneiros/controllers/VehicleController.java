package caroneiros.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import caroneiros.domain.models.Vehicle;
import caroneiros.services.VehicleService;
import lombok.extern.log4j.Log4j2;

@RequestMapping(value = "/vehicles")
@RestController
@Log4j2
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    //Entra em recursao
    @GetMapping(value = "/{id}")
    private ResponseEntity<Vehicle> findVehicleById(@PathVariable Long id){
        log.info("Requisição do tipo GET para baseUrl/vehicles/{}" , id);
        Vehicle vehicleFound = vehicleService.findVehicleById(id);
        return ResponseEntity.ok().body(vehicleFound);
    }
}
