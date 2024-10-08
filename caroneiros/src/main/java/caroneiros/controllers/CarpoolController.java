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

import caroneiros.dtos.carpool.CarpoolRequestDTO;
import caroneiros.dtos.carpool.CarpoolResponseDTO;
import caroneiros.dtos.carpool.CarpoolUpdateRequestDTO;
import caroneiros.services.carpool.CarpoolService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(value = "/carpools")
public class CarpoolController {

    @Autowired
    private CarpoolService carpoolService;

    @PostMapping(value = "/users/{driverId}/vehicles/{vehicleId}")
    public ResponseEntity<CarpoolResponseDTO> saveCarpool(@PathVariable Long driverId, @PathVariable Long vehicleId,
           @Valid @RequestBody CarpoolRequestDTO carpoolRequestDTO) {
        log.info("Requisição do tipo POST para baseUrl/carpools");
        CarpoolResponseDTO carpoolResponseDTO = carpoolService.saveCarpool(driverId, vehicleId, carpoolRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(carpoolResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<CarpoolResponseDTO>> findAvaliableCarpoolsByDate(
            @RequestParam(required = false) LocalDate date) {
        LocalDate filterDate = (date != null) ? date : LocalDate.now();
        log.info("Requisição do tipo GET  para baseUrl/carpools?date={}", filterDate);
        List<CarpoolResponseDTO> carpoolsAvaliables = carpoolService.findCarpoolsByDate(filterDate);
        return ResponseEntity.ok().body(carpoolsAvaliables);

    }

    @DeleteMapping(value = "/{carpoolId}/users/{driverId}")
    public ResponseEntity<Void> deleteCarpool(@PathVariable Long driverId, @PathVariable Long carpoolId) {
        log.info("Requisião do tipo DELETE para baseUrl/carpools/{}", carpoolId);
        carpoolService.deleteCarpoolById(carpoolId, driverId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{carpoolId}/users/{driverId}")
    public ResponseEntity<CarpoolResponseDTO> updateCarpool(@PathVariable Long driverId, @PathVariable Long carpoolId,
          @Valid  @RequestBody CarpoolUpdateRequestDTO carpoolRequestDTO) {
        log.info("Requisião do tipo PATCH para baseUrl/carpools/{}", carpoolId);
        carpoolService.updateCarpool(carpoolId, driverId, carpoolRequestDTO);
        return ResponseEntity.noContent().build();
    }
}
