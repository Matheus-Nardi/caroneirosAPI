package caroneiros.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import caroneiros.dtos.reservation.ReservationRequestDTO;
import caroneiros.dtos.reservation.ReservationResponseDTO;
import caroneiros.services.reservation.CarpoolReservationService;
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

    @GetMapping(value = "/{carpoolReservationId}")
    public ResponseEntity<ReservationResponseDTO> findCarpoolReservationById(@PathVariable Long carpoolReservationId) {
        log.info("Requisição do tipo GET para baseUrl/reservations/{}", carpoolReservationId);
        ReservationResponseDTO carpoolReservation = reservationService.findCarpoolReservationById(carpoolReservationId);
        return ResponseEntity.ok().body(carpoolReservation);
    }

    @GetMapping("/carpools/{carpoolId}")
    public ResponseEntity<List<ReservationResponseDTO>> findAllReservationsByCarpool(@PathVariable Long carpoolId) {
        log.info("Requisição do tipo GET para baseUrl/reservations/{}", carpoolId);
        List<ReservationResponseDTO> carpoolReservation = reservationService.findReservationsByCarpoolId(carpoolId);
        return ResponseEntity.ok().body(carpoolReservation);
    }

    @DeleteMapping(value = "/{carpoolReservationId}")
    public ResponseEntity<Void> cancelCarpoolReservation(@PathVariable Long carpoolReservationId) {
        log.info("Requisião do tipo DELETE para reservation/{}", carpoolReservationId);
        reservationService.cancelReserveCarpool(carpoolReservationId);
        return ResponseEntity.noContent().build();
    }
}
