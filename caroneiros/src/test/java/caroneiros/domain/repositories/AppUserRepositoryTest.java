package caroneiros.domain.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import caroneiros.domain.models.AppUser;
import jakarta.validation.ConstraintViolationException;

@DataJpaTest
public class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository userRepository;

    private AppUser user;

    @BeforeEach
    void setUp() {
        user = AppUser.builder()
                .name("Carlos")
                .cpf("31081883057")
                .driver(true)
                .phone("8235679591")
                .bio("Oi, sou o Carlos")
                .build();
    }

    @Test
    @DisplayName("Should successfully create an AppUser")
    public void shouldCreateAppUserSuccessfully() {
        AppUser userSaved = userRepository.save(user);
        Long id = userSaved.getId();
        assertNotNull(userSaved.getId());
        assertEquals(user, userSaved);
        assertEquals(user.getId(), id);
    }

    @Test
    @DisplayName("Should throw exception when creating an AppUser with missing values")
    public void shouldThrowExceptionWhenCreateAppUserWithMissingValues() {
        AppUser appUser = AppUser.builder()
                .name("")
                .cpf("")
                .driver(false)
                .phone("")
                .bio("")
                .build();

        assertThrows(ConstraintViolationException.class, () -> {
            userRepository.save(appUser);
        });
    }

    @Test
    @DisplayName("Should throw exception when creating an AppUser with an invalid phone number")
    public void shouldThrowExceptionWhenCreateAppUserWithInvalidPhone() {
        user.setPhone("123456789101");

        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(user);
        });
    }

    @Test
    @DisplayName("Should throw exception when creating an AppUser with an invalid CPF")
    public void shouldThrowExceptionWhenCreateAppUserWithInvalidCPF() {
        user.setCpf("12345678901");

        assertThrows(ConstraintViolationException.class, () -> {
            userRepository.save(user);
        });
    }

    @Test
    @DisplayName("Should throw exception when creating an AppUser with duplicate CPF")
    public void shouldThrowExceptionWhenCreateAppUserWithDuplicateCPF() {
        
        userRepository.save(user);

        AppUser appUserDuplicate = AppUser.builder()
                .name("Carlos Duplicate")
                .cpf("31081883057")
                .driver(true)
                .phone("8235679591")
                .bio("Motorista há 3 anos")
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(appUserDuplicate);
        });
    }
}
