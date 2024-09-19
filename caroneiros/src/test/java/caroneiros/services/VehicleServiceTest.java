package caroneiros.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import caroneiros.domain.models.Vehicle;
import caroneiros.infra.exceptions.NotFoundException;
import caroneiros.repositories.VehicleRepository;
import caroneiros.services.vehicle.VehicleService;

@SpringBootTest
public class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleService vehicleService;

    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        vehicle = Vehicle.builder()
                .id(1l)
                .model("Fiesta")
                .build();
    }

    @Test
    @DisplayName("Should delete vehicle successfully when it exists")
    void shouldDeleteVehicleSuccessfullyWhenItExists() {
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(vehicle));
        vehicleService.deleteVehicleById(1l);
        verify(vehicleRepository, times(1)).findById(1l);
        verify(vehicleRepository, times(1)).delete(vehicle);
    }

    @Test
    @DisplayName("Should throw NotFoundException when trying to delete a non-existing vehicle")
    void shouldThrowNotFoundExceptionWhenDeletingNonExistentVehicle() {
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrowsExactly(NotFoundException.class, () -> vehicleService.deleteVehicleById(1l));
        verify(vehicleRepository, times(1)).findById(1l);
    }

    @Test
    @DisplayName("Should return vehicle by ID when it exists")
    void shouldReturnVehicleByIdWhenItExists() {
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(vehicle));

        Vehicle vehicleFound = vehicleService.findVehicleById(1l);
        assertEquals(vehicleFound, vehicle);
        assertEquals(1l, vehicleFound.getId());

        verify(vehicleRepository, times(1)).findById(1l);
    }

    @Test
    @DisplayName("Should throw NotFoundException when vehicle is not found by ID")
    void shouldThrowNotFoundExceptionWhenVehicleNotFoundById() {
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrowsExactly(NotFoundException.class, () -> vehicleService.findVehicleById(1l));
        verify(vehicleRepository, times(1)).findById(1l);
    }

 
}
