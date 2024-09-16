package caroneiros.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import caroneiros.dtos.ReservationRequestDTO;
import caroneiros.dtos.ReservationResponseDTO;
import caroneiros.services.CarpoolReservationService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(value = "/reservations")
public class CarpoolReservationController {

    @Autowired
    private CarpoolReservationService reservationService;

    @PostMapping(value = "/users/{userId}/carpools/{carpoolId}")
    public ResponseEntity<ReservationResponseDTO> reserveCarpool(@PathVariable Long userId,
            @PathVariable Long carpoolId, @RequestBody ReservationRequestDTO dto) {
        log.info("Requisição do tipo POST para baseUrl/users/{}/carpools/{}", userId, carpoolId);
        ReservationResponseDTO carpoolReservation = reservationService.reserveCarpool(userId, carpoolId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(carpoolReservation);
    }

    @DeleteMapping(value = "{carpoolReservationId}")
    public ResponseEntity<Void> cancelCarpoolReservation(@ PathVariable Long carpoolReservationId){
        log.info("Requisião do tipo DELETE para reservation/{}" , carpoolReservationId);
        reservationService.cancelCarpool(carpoolReservationId);
        return ResponseEntity.noContent().build();
    }
}
