package caroneiros.domain.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Vehicle;
import caroneiros.repositories.AppUserRepository;
import caroneiros.repositories.VehicleRepository;
import jakarta.validation.ConstraintViolationException;

@DataJpaTest
public class VehicleRepositoryTest {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    private AppUser driver;
    private Vehicle vehicle;

    @BeforeEach
    public void setUp() {

        driver = AppUser.builder()
                .name("John Doe")
                .cpf("10356690059")
                .phone("1234567890")
                .driver(true)
                .build();
        userRepository.save(driver);

        vehicle = Vehicle.builder()
                .driver(driver)
                .licensePlate("ABC1234")
                .model("Sedan")
                .color("Blue")
                .build();

    }

    @Test
    @DisplayName("Should throw a ConstraintViolationException when required fields are missing")
    public void createVehicleWithMissingFields() {
        Vehicle vehicle = Vehicle.builder()
                .driver(driver)
                .licensePlate("")
                .model("")
                .color("")
                .build();

        assertThrows(ConstraintViolationException.class, () -> {
            vehicleRepository.save(vehicle);
        });
    }

    @Test
    @DisplayName("Should throw a DataIntegrityViolationException if have two vehicles with the same plate")
    public void shouldThrowExceptionIfLicensePlateIsNotUnique() {
        Vehicle vehicle1 = Vehicle.builder()
                .driver(driver)
                .licensePlate("ABC1234")
                .model("Sedan")
                .color("Blue")
                .build();

        Vehicle vehicle2 = Vehicle.builder()
                .driver(driver)
                .licensePlate("ABC1234")
                .model("SUV")
                .color("Red")
                .build();

        vehicleRepository.save(vehicle1);

        assertThrows(DataIntegrityViolationException.class, () -> {
            vehicleRepository.save(vehicle2);
        });
    }

    @Test
    @DisplayName("Should successfully create a vehicle with a license plate matching format [A-Z]{3}[0-9]{4}")
    public void shouldCreateVehicleWithLiscentePlateFormat1Case1() {
        String format1 = "[A-Z]{3}[0-9]{4}";
        vehicleRepository.save(vehicle);
        Optional<Vehicle> vehicleFromDB = vehicleRepository.findById(1l);

        assertTrue(vehicleFromDB.isPresent());
        assertEquals(vehicle, vehicleFromDB.get());
        assertTrue(vehicleFromDB.get().getLicensePlate().matches(format1));
    }

    @Test
    @DisplayName("Should throw a ConstraintViolationException when trying to create a vehicle with an invalid license plate format")
    public void shouldThrowExceptionWhenCreateVehicleWithLiscentePlateFormat1Case2() {

        vehicle.setLicensePlate("ABC123X");

        assertThrows(ConstraintViolationException.class, () -> {
            vehicleRepository.save(vehicle);
        });
    }

    @Test
    @DisplayName("Should successfully create a vehicle with a license plate matching format [A-Z]{3}[0-9][A-Z][0-9]{2}")
    public void shoudCreateVehicleWithLiscentePlateFormat2Case1() {
        String format2 = "[A-Z]{3}[0-9][A-Z][0-9]{2}";

        vehicle.setLicensePlate("ABC1D23");
        Vehicle vehicleSaved = vehicleRepository.save(vehicle);
        Long id = vehicleSaved.getId();

        Optional<Vehicle> vehicleFromDB = vehicleRepository.findById(id);

        assertTrue(vehicleFromDB.isPresent());
        assertEquals(vehicle, vehicleFromDB.get());
        assertTrue(vehicleFromDB.get().getLicensePlate().matches(format2));
    }

    @Test
    @DisplayName("Should throw a ConstraintViolationException when trying to create a vehicle with an invalid license plate format")
    public void shouldThrowExceptionWhenCreateVehicleWithLiscentePlateFormat2Case2() {

        vehicle.setLicensePlate("ABC1D2A");

        assertThrows(ConstraintViolationException.class, () -> {
            vehicleRepository.save(vehicle);
        });
    }

}
