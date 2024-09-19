package caroneiros.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Carpool;
import caroneiros.dtos.carpool.CarpoolRequestDTO;
import caroneiros.infra.exceptions.DontDriverException;
import caroneiros.infra.exceptions.NotFoundException;
import caroneiros.repositories.CarpoolRepository;
import caroneiros.services.appuser.AppUserService;
import caroneiros.services.carpool.CarpoolService;

public class CarpoolServiceTest {

    @Mock
    private AppUserService appUserService;

    @Mock
    private CarpoolRepository carpoolRepository;

    @InjectMocks
    private CarpoolService carpoolService;

    private AppUser driver;

    private CarpoolRequestDTO carpoolRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        driver = AppUser.builder()
                .id(1l)
                .driver(true)
                .name("Carlos")
                .build();

        carpoolRequestDTO = new CarpoolRequestDTO(driver.getId(), LocalDateTime.now(), null, null);

    }

    @Test
    @DisplayName("Should save carpool")
    void shouldSaveCarpool() {
        Carpool carpool = new Carpool();
        carpool.setDriver(driver);
        when(appUserService.findUserById(driver.getId())).thenReturn(driver);
        when(carpoolRepository.save(any(Carpool.class))).thenReturn(carpool);

        Carpool carpoolSaved = carpoolService.saveCarpool(carpoolRequestDTO);
        assertTrue(carpoolSaved.getDriver().isDriver());

        verify(appUserService, times(1)).findUserById(1l);
        verify(carpoolRepository, times(1)).save(any(Carpool.class));
    }

    @Test
    @DisplayName("Should throw DontDriverException when user is not a driver")
    void shouldThrowDontDriverExceptionWhenUserIsNotDriver() {
        Carpool carpool = new Carpool();
        driver.setDriver(false);
        carpool.setDriver(driver);
        when(appUserService.findUserById(driver.getId())).thenReturn(driver);
        when(carpoolRepository.save(any(Carpool.class))).thenReturn(carpool);

        assertThrows(DontDriverException.class, () -> carpoolService.saveCarpool(carpoolRequestDTO));

        verify(appUserService, times(1)).findUserById(1l);
        verify(carpoolRepository, times(0)).save(any(Carpool.class));
    }

    @Test
    void testCompleteCarpool() {

    }

    @Test
    void testDeleteCarpoolById() {
        Carpool carpool = new Carpool();
        carpool.setId(1l);

        when(carpoolRepository.findById(anyLong())).thenReturn(Optional.of(carpool));

        carpoolService.deleteCarpoolById(1l);

        verify(carpoolRepository, times(1)).findById(1L);
        verify(carpoolRepository, times(1)).delete(carpool);
    }
    @Test
    void testDeleteCarpoolByIdWhenNotFound() {
        when(carpoolRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> carpoolService.deleteCarpoolById(1l));


        verify(carpoolRepository, times(1)).findById(1L);
        verify(carpoolRepository, never()).delete(any(Carpool.class));
    }

    @Test
    void testFindAvailableCarpools() {
        Carpool carpoolAvailable1 = new Carpool();
        carpoolAvailable1.setSeatsAvailable(4);

        Carpool carpoolAvailable2 = new Carpool();
        carpoolAvailable2.setSeatsAvailable(2);

        Carpool carpoolNotAvailable = new Carpool();
        carpoolNotAvailable.setSeatsAvailable(0);

        List<Carpool> carpoolsAvailable = List.of(carpoolAvailable1, carpoolAvailable2);

        when(carpoolRepository.findAvailableCarpools()).thenReturn(carpoolsAvailable);

        List<Carpool> carpoolsFound = carpoolService.findAvailableCarpools();

        assertEquals(2, carpoolsFound.size());
        assertTrue(carpoolsFound.contains(carpoolAvailable1));
        assertTrue(carpoolsFound.contains(carpoolAvailable2));
        assertFalse(carpoolsFound.contains(carpoolNotAvailable));

        verify(carpoolRepository, times(1)).findAvailableCarpools();
    }

    @Test
    void testFindAvailableCarpoolsWhenNoneAvailable() {
        when(carpoolRepository.findAvailableCarpools()).thenReturn(Collections.emptyList());

        List<Carpool> carpoolsFound = carpoolService.findAvailableCarpools();

        assertTrue(carpoolsFound.isEmpty());
        verify(carpoolRepository, times(1)).findAvailableCarpools();
    }

    @Test
    void testFindCarpoolById() {
        Carpool carpool = new Carpool();
        carpool.setId(1l);
        when(carpoolRepository.findById(anyLong())).thenReturn(Optional.of(carpool));
        Carpool carpoolFound = carpoolService.findCarpoolById(1l);
        verify(carpoolRepository, times(1)).findById(1l);

        assertEquals(carpoolFound.getId(), 1l);
    }

    @Test
    void testIsDriverAvailable() {

    }

    @Test
    void testUptadeCarpool() {

    }
}
