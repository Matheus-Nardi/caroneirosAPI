package caroneiros.services.review;

import caroneiros.dtos.review.ReviewRequestDTO;
import caroneiros.dtos.review.ReviewResponseDTO;

public interface ReviewService {

    ReviewResponseDTO reviewDriver(Long ratedDriver, Long reviewingPassenger, ReviewRequestDTO dto);
}
