package caroneiros.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import caroneiros.domain.repositories.VehicleRepository;
import caroneiros.infra.exception.NotFoundAppUserException;

@SpringBootTest
public class AppUserServiceTest {

    @Mock
    private AppUserRepository userRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private AppUserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should save user successfully")
    void shouldSaveUserSuccessfully() {
        AppUser user = AppUser.builder()
                .id(1l)
                .name("Raul")
                .build();

        when(userRepository.save(user)).thenReturn(user);
        userService.saveUser(user);
        verify(userRepository, times(1)).save(user);

        assertEquals(user.getName(), "Raul");
    }

    @Test
    @DisplayName("Should return null when saving user fails")
    void shouldReturnNullWhenSaveUserFails() {
        AppUser user = AppUser.builder()
                .id(1l)
                .name("Raul")
                .build();

        when(userRepository.save(user)).thenReturn(null);
        AppUser userSaved = userRepository.save(user);
        verify(userRepository, times(1)).save(user);
        assertNull(userSaved);
    }

    @Test
    @DisplayName("Should return user by ID")
     void shouldReturnUserById() {
        AppUser user = AppUser.builder()
                .id(1l)
                .name("Raul")
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        AppUser userFind = userService.findUserById(1l);
        verify(userRepository, times(1)).findById(1l);
        assertEquals(user, userFind);
        assertEquals(1l, userFind.getId());
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
        AppUser user = AppUser.builder()
                .id(1L)
                .name("Raul")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.deleteUserById(1L);
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).delete(user);
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
        AppUser driver = AppUser.builder()
                .id(1l)
                .name("Raul")
                .driver(true)
                .vehicles(new ArrayList<>())
                .build();

        Vehicle vehicle = Vehicle.builder()
                .driver(driver)
                .licensePlate("ABC1234")
                .model("Sedan")
                .color("Blue")
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(driver));

        userService.registerVehicle(1l, vehicle);
        
        assertTrue(driver.getVehicles().contains(vehicle));
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(driver);

    }
}
