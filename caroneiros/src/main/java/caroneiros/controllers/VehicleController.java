package caroneiros.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import caroneiros.domain.models.Vehicle;
import caroneiros.dtos.vehicle.VehiclesResponseDTO;
import caroneiros.services.vehicle.VehicleService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(value = "/users/{userId}/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehiclesResponseDTO> registerVehicle(@PathVariable Long userId,
            @Valid @RequestBody Vehicle vehicle) {
        log.info("Requisição do tipo POST para baseUrl/users/{}/vehicles", userId);
        VehiclesResponseDTO vehicleSaved = vehicleService.registerVehicle(userId, vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleSaved);
    }

    @GetMapping
    public ResponseEntity<List<VehiclesResponseDTO>> findAllVehiclesByUser(@PathVariable Long userId) {
        log.info("Requisição do tipo GET para baseUrl/users/{}/vehicles", userId);
        List<VehiclesResponseDTO> vehicles = vehicleService.findAllVehicleByUser(userId);
        return ResponseEntity.ok().body(vehicles);
    }

    @PatchMapping(value = "/{vehicleId}")
    public ResponseEntity<VehiclesResponseDTO> updateVehicle(@PathVariable Long userId,
            @PathVariable Long vehicleId,
            @RequestBody Vehicle vehicleToUpdate) {
        log.info("Requisição do tipo PATCH para baseUrl/users/{}/vehicles/{}", userId, vehicleId);
        vehicleService.updateVehicle(userId, vehicleId, vehicleToUpdate);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{vehicleId}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long userId, @PathVariable Long vehicleId) {
        log.info("Requisição do tipo DELETE para baseUrl/users/{}/vehicles/{}", userId, vehicleId);
        vehicleService.deleteVehicleById(userId, vehicleId);
        return ResponseEntity.noContent().build();
    }
}
