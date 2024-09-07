package caroneiros.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import caroneiros.domain.models.Vehicle;
import caroneiros.domain.repositories.VehicleRepository;

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
        .model("Ford")
        .build();
    }

    @Test
    void shouldDeleteVehicle(){
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(vehicle));
        vehicleService.deleteVehicleById(1l);
        verify(vehicleRepository,times(1)).findById(1l);
        verify(vehicleRepository,times(1)).delete(vehicle);
    }
}
