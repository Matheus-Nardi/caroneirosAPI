package caroneiros.domain.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Carpool;
import jakarta.validation.ConstraintViolationException;

@DataJpaTest
public class CarpoolRepositoryTest {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private CarpoolRepository carpoolRepository;

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

        carpool = Carpool.builder()
            .driver(driver)
            .estimatedDeparture(estimatedDeparture)
            .estimatedArrival(estimatedDeparture.plusHours(1l))
            .estimadedPrice(30.00)
            .seatsAvailable(4)
            .build();
    }

    @Test
    @DisplayName("Should successfully create a Carpool")
    public void testCreateCarpool() {
        Carpool carpoolSaved = carpoolRepository.save(carpool);
        Long id = carpoolSaved.getId();
        assertNotNull(id);
        Optional<Carpool> carpoolFromDB = carpoolRepository.findById(id);
        assertTrue(carpoolFromDB.isPresent());
        assertEquals(carpoolSaved, carpoolFromDB.get());
    }

    @Test
    @DisplayName("Should throw ConstraintViolationException when creating Carpool with null fields")
    public void testThrowExceptionWhenCreateCarpoolWithNullFields() {
        carpool.setEstimatedDeparture(null);
        carpool.setSeatsAvailable(null);
        assertThrows(ConstraintViolationException.class, () -> carpoolRepository.save(carpool));
    }

    @Test
    @DisplayName("Should throw ConstraintViolationException when creating Carpool with seats less than minimum")
    public void testThrowExceptionWhenCreateCarpoolWithInvalidSeatsMin() {
        carpool.setSeatsAvailable(0);
        assertThrows(ConstraintViolationException.class, () -> carpoolRepository.save(carpool));
    }

    @Test
    @DisplayName("Should throw ConstraintViolationException when creating Carpool with seats more than maximum")
    public void testThrowExceptionWhenCreateCarpoolWithInvalidSeatsMax() {
        carpool.setSeatsAvailable(5);
        assertThrows(ConstraintViolationException.class, () -> carpoolRepository.save(carpool));
    }
}
