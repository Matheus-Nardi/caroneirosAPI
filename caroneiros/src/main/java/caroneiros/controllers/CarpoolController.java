package caroneiros.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import caroneiros.domain.models.Carpool;
import caroneiros.dtos.CarpoolRequestDTO;
import caroneiros.dtos.CarpoolResponseDTO;
import caroneiros.services.CarpoolService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(value = "/caronas")
public class CarpoolController {

    @Autowired
    private CarpoolService carpoolService;

    @PostMapping
    public ResponseEntity<CarpoolResponseDTO> saveCarpool(@RequestBody CarpoolRequestDTO carpoolRequestDTO) {
        log.info("Requisição do tipo POST para baseUrl/caronas");
        CarpoolResponseDTO carpoolResponseDTO = carpoolService.saveCarpool(carpoolRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(carpoolResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<Carpool>> findAvaliableCarpools() {
        log.info("Requisição do tipo GET para baseUrl/caronas");
        List<Carpool> carpoolsAvaliables = carpoolService.findAvailableCarpools();
        return ResponseEntity.ok().body(carpoolsAvaliables);

    }
}
