package caroneiros.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Carpool;
import caroneiros.domain.repositories.CarpoolRepository;

public class CarpoolServiceTest {

    @Mock
    private AppUserService appUserService;

    @Mock
    private CarpoolRepository carpoolRepository;

    @InjectMocks
    private CarpoolService carpoolService;

    private AppUser driver;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        driver = AppUser.builder()
        .id(1l)
        .driver(true)
        .name("Carlos")
        .build();
    }

    @Test
    void testCompleteCarpool() {

    }

    @Test
    void testDeleteCarpoolById() {

    }

    @Test
    void testFindAvailableCarpools() {

    }

    @Test
    void testFindCarpoolById() {

    }

    @Test
    void testIsDriverAvailable() {

    }

    @Test
    void testSaveCarpool() {
        Carpool carpool = new Carpool();
        when(appUserService.findUserById(driver.getId())).thenReturn(driver);
        when(carpoolRepository.save(any(Carpool.class))).thenReturn(carpool);
    }

    @Test
    void testUptadeCarpool() {

    }
}
