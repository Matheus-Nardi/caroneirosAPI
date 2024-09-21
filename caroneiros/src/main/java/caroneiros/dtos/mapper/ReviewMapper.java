package caroneiros.dtos.mapper;

import caroneiros.domain.models.Review;
import caroneiros.dtos.review.ReviewResponseDTO;

public class ReviewMapper {

    public static ReviewResponseDTO toReviewResponseDTO(Review review) {
        return new ReviewResponseDTO(
                review.getId(),
                review.getRatedDriver().getId(),
                review.getReviewingPassenger().getId(),
                review.getScore(),
                review.getDescription());
    }
}
