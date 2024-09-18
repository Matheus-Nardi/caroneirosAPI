package caroneiros.controllers;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import caroneiros.domain.models.Carpool;
import caroneiros.dtos.CarpoolRequestDTO;
import caroneiros.dtos.CarpoolResponseDTO;
import caroneiros.dtos.CarpoolUpdateRequestDTO;
import caroneiros.services.CarpoolService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(value = "/carpools")
public class CarpoolController {

    @Autowired
    private CarpoolService carpoolService;

    @PostMapping(value = "/users/{driverId}/vehicles/{vehicleId}")
    public ResponseEntity<CarpoolResponseDTO> saveCarpool(@PathVariable Long driverId, @PathVariable Long vehicleId,
            @RequestBody CarpoolRequestDTO carpoolRequestDTO) {
        log.info("Requisição do tipo POST para baseUrl/carpools");
        CarpoolResponseDTO carpoolResponseDTO = carpoolService.saveCarpool(driverId, vehicleId, carpoolRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(carpoolResponseDTO);
    }

    /*
     * 
     @GetMapping
     public ResponseEntity<List<CarpoolResponseDTO>> findAvaliableCarpools() {
        log.info("Requisição do tipo GET para baseUrl/carpools");
        List<CarpoolResponseDTO> carpoolsAvaliables = carpoolService.findAvailableCarpools();
        return ResponseEntity.ok().body(carpoolsAvaliables);
        
    }
    */

    @GetMapping(value = "/today")
    public ResponseEntity<List<CarpoolResponseDTO>> findAvaliableCarpoolsForToday() {
        log.info("Requisição do tipo GET para baseUrl/carpools/today");
        List<CarpoolResponseDTO> carpoolsAvaliables = carpoolService.filterCarpoolsForToday();
        return ResponseEntity.ok().body(carpoolsAvaliables);

    }

    @GetMapping()
    public ResponseEntity<List<CarpoolResponseDTO>> findAvaliableCarpoolsByDate(@RequestParam  LocalDate date) {
        log.info("Requisição do tipo GET  para baseUrl/carpools?date={}", date);
        List<CarpoolResponseDTO> carpoolsAvaliables = carpoolService.findCarpoolsByDate(date);
        return ResponseEntity.ok().body(carpoolsAvaliables);

    }

    @DeleteMapping(value = "/{carpoolId}")
    public ResponseEntity<Void> deleteCarpool(@PathVariable Long carpoolId) {
        log.info("Requisião do tipo DELETE para baseUrl/carpools/{}", carpoolId);
        carpoolService.deleteCarpoolById(carpoolId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{carpoolId}")
    public ResponseEntity<Carpool> updateCarpool(@PathVariable Long carpoolId,
            @RequestBody CarpoolUpdateRequestDTO carpoolRequestDTO) {
        log.info("Requisião do tipo UPDATE para baseUrl/carpools/{}", carpoolId);
        Carpool carpool = carpoolService.updateCarpool(carpoolId, carpoolRequestDTO);
        return ResponseEntity.ok().body(carpool);
    }
}
