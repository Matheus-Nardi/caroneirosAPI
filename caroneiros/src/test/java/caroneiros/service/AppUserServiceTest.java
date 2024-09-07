package caroneiros.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
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

import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Vehicle;
import caroneiros.domain.repositories.AppUserRepository;
import caroneiros.infra.exception.DontDriverException;
import caroneiros.infra.exception.NotFoundAppUserException;

@SpringBootTest
public class AppUserServiceTest {

    @Mock
    private AppUserRepository userRepository;

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private AppUserService userService;

    private AppUser driver;
    private AppUser nonDriver;
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        driver = AppUser.builder()
                .id(1l)
                .name("Motorista")
                .driver(true)
                .build();

        nonDriver = AppUser.builder()
                .id(2l)
                .name("Passageiro")
                .driver(false)
                .build();

        vehicle = Vehicle.builder()
                .model("Ford")
                .build();
    }

    @Test
    @DisplayName("Should save user successfully")
    void shouldSaveUserSuccessfully() {
        when(userRepository.save(nonDriver)).thenReturn(nonDriver);
        userService.saveUser(nonDriver);
        verify(userRepository, times(1)).save(nonDriver);

        assertEquals(nonDriver.getName(), "Passageiro");
    }

    @Test
    @DisplayName("Should return null when saving user fails")
    void shouldReturnNullWhenSaveUserFails() {

        when(userRepository.save(nonDriver)).thenReturn(null);
        AppUser userSaved = userRepository.save(nonDriver);
        verify(userRepository, times(1)).save(nonDriver);
        assertNull(userSaved);
    }

    @Test
    @DisplayName("Should return user by ID")
    void shouldReturnUserById() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(nonDriver));
        AppUser userFind = userService.findUserById(1l);
        verify(userRepository, times(1)).findById(1l);
        assertEquals(nonDriver, userFind);
        assertEquals(2l, userFind.getId());
    }

    @Test
    @DisplayName("Should throw exception when user not found by ID")
    public void shouldThrowExceptionWhenUserNotFoundById() {
        when(userRepository.findById(anyLong())).thenThrow(NotFoundAppUserException.class);
        assertThrowsExactly(NotFoundAppUserException.class, () -> userService.findUserById(1L));
        verify(userRepository, times(1)).findById(1l);
    }

    @Test
    @DisplayName("Should delete user by ID successfully")
    public void shouldDeleteUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(nonDriver));
        userService.deleteUserById(1L);
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).delete(nonDriver);
    }

    @Test
    @DisplayName("Should throw exception when attempting to delete a non-existent user")
    public void shouldThrowExceptionWhenDeletingNonExistentUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrowsExactly(NotFoundAppUserException.class, () -> userService.deleteUserById(1L));

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).delete(any(AppUser.class));
    }

    @Test
    @DisplayName("Should register a vehicle to a driver")
    public void shouldRegisterVehicleToDriver() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(driver));

        userService.registerVehicle(1l, vehicle);

        verify(vehicleService, times(1)).addVehicleForUser(driver, vehicle);

    }

    @Test
    @DisplayName("Should throw DontDriverException when attempting to register a vehicle for a non-driver user")
    public void shouldThrowDontDriverExceptionWhenNonDriverTriesToRegisterVehicle() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(nonDriver));

        assertThrowsExactly(DontDriverException.class, () -> userService.registerVehicle(1l, new Vehicle()));

        assertFalse(nonDriver.isDriver());
        verify(vehicleService, never()).addVehicleForUser(any(), any());

    }
}
