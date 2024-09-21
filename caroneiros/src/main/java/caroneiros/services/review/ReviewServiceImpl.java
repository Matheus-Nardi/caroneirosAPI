package caroneiros.services.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Review;
import caroneiros.dtos.mapper.ReviewMapper;
import caroneiros.dtos.review.ReviewRequestDTO;
import caroneiros.dtos.review.ReviewResponseDTO;
import caroneiros.infra.exceptions.InvalidOperationException;
import caroneiros.repositories.ReviewRepository;
import caroneiros.services.appuser.AppUserService;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private AppUserService userService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public ReviewResponseDTO reviewDriver(Long ratedDriver, Long reviewingPassenger, ReviewRequestDTO dto) {

        if(ratedDriver.equals(reviewingPassenger))
            throw new InvalidOperationException("O motorista não pode ser avaliado por ele mesmo!");
        AppUser driver = userService.findUserById(ratedDriver);
        AppUser passenger = userService.findUserById(reviewingPassenger);

        Review review = Review.builder().ratedDriver(driver)
                .reviewingPassenger(passenger)
                .score(dto.score())
                .description(dto.description())
                .build();
                reviewRepository.save(review);
                driver.setScore(reviewRepository.mediaDriverScore(ratedDriver));
                userService.saveUser(driver);
        return ReviewMapper.toReviewResponseDTO(review);
    }

}
