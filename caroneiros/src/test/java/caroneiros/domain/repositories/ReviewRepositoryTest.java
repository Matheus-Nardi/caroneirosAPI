package caroneiros.domain.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Review;
import caroneiros.repositories.AppUserRepository;
import caroneiros.repositories.ReviewRepository;

@DataJpaTest
public class ReviewRepositoryTest {

    @Autowired
    public AppUserRepository userRepository;

    @Autowired
    public ReviewRepository reviewRepository;

    private AppUser passenger;
    private AppUser driver;
    private Review reviewToSave;

    @BeforeEach
    public void setUp() {
        passenger = AppUser.builder()
                .name("Joao")
                .bio("Sou chegado em bater papo com o motorista")
                .driver(false)
                .cpf("18402100015")
                .phone("6339233235")
                .build();

        driver = AppUser.builder()
                .name("Carlos")
                .cpf("31081883057")
                .driver(true)
                .phone("8235679591")
                .bio("Oi, sou o Carlos")
                .build();

        reviewToSave = Review.builder()
                .ratedDriver(driver)
                .reviewingPassenger(passenger)
                .score(4)
                .description("Muito bom!")
                .build();
    }

    @Test
    @DisplayName("Should successfully create a review and verify its properties")
    public void shouldSuccessfullyCreateReviewAndVerifyProperties() {
        userRepository.save(passenger);
        userRepository.save(driver);

        Review reviewSaved = reviewRepository.save(reviewToSave);
        Long id = reviewSaved.getId();

        Review reviewFromDB = reviewRepository.findById(id).get();
        assertNotNull(id);
        assertTrue(reviewRepository.findById(id).isPresent());
        assertEquals(reviewToSave, reviewFromDB);

        assertEquals(driver, reviewFromDB.getRatedDriver());
        assertEquals(passenger, reviewFromDB.getReviewingPassenger());
        assertEquals(4, reviewFromDB.getScore());
    }

    @Test
    public void shouldThrowExceptionWhenCreateReviewWithInvalidMinScore() {
        reviewToSave.setScore(0);
        
        assertThrows(InvalidDataAccessApiUsageException.class, () -> reviewRepository.save(reviewToSave));
    }
    @Test
    public void shouldThrowExceptionWhenCreateReviewWithInvalidMaxScore() {
        reviewToSave.setScore(6);
        
        assertThrows(InvalidDataAccessApiUsageException.class, () -> reviewRepository.save(reviewToSave));
    }

    @Test
    public void shouldFailCreateReviewWithMissingValues(){
        Review failReview = Review.builder()
        .ratedDriver(driver)
        .reviewingPassenger(null)
        .score(null)
        .description("Aqui pode ser nulo")
        .build();

        assertThrows(InvalidDataAccessApiUsageException.class, () -> reviewRepository.save(failReview));
    }
}
