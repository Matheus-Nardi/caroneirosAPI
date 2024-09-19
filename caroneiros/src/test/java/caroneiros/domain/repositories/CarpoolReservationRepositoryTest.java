package caroneiros.domain.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Carpool;
import caroneiros.domain.models.CarpoolReservation;
import caroneiros.domain.models.CarpoolStatus;
import caroneiros.repositories.AppUserRepository;
import caroneiros.repositories.CarpoolRepository;
import caroneiros.repositories.CarpoolReservationRepository;

@DataJpaTest
public class CarpoolReservationRepositoryTest {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private CarpoolRepository carpoolRepository;

    @Autowired
    private CarpoolReservationRepository reservationRepository;

    private AppUser passenger;
    private AppUser driver;
    private Carpool carpool;

    @BeforeEach
    public void setUp() {
        LocalDateTime estimatedDeparture = LocalDateTime.now();
        driver = AppUser.builder()
            .name("Carlos")
            .cpf("31081883057")
            .driver(true)
            .phone("8235679591")
            .bio("Oi, sou o Carlos")
            .build();

        userRepository.save(driver);

        passenger = AppUser.builder()
            .name("Militao")
            .cpf("68223006006")
            .driver(false)
            .phone("8329333672")
            .bio("Oi, sou o Militão")
            .build();
        userRepository.save(passenger);

        carpool = Carpool.builder()
            .driver(driver)
            .estimatedDeparture(estimatedDeparture)
            .estimatedArrival(estimatedDeparture.plusHours(1L))
            .estimadedPrice(30.00)
            .seatsAvailable(4)
            .build();

        carpoolRepository.save(carpool);
    }

    @Test
    @DisplayName("Should successfully create a Carpool Reservation")
    public void testCreateReservation() {
        CarpoolReservation reservation = CarpoolReservation.builder()
            .passenger(passenger)
            .carpool(carpool)
            .status(CarpoolStatus.CONFIRMED)
            .build();

        reservationRepository.save(reservation);
    }

    @Test
    @DisplayName("Should throw DataIntegrityViolationException when creating a Reservation with null fields")
    public void testThrowExceptionWhenCreateInvalidReservation() {
        CarpoolReservation reservation = CarpoolReservation.builder()
            .passenger(null)
            .carpool(null)
            .status(CarpoolStatus.CONFIRMED)
            .build();

        assertThrows(DataIntegrityViolationException.class, () -> reservationRepository.save(reservation));
    }
}
