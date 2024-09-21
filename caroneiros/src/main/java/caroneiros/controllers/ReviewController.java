package caroneiros.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import caroneiros.dtos.review.ReviewRequestDTO;
import caroneiros.dtos.review.ReviewResponseDTO;
import caroneiros.services.review.ReviewService;

@RestController
@RequestMapping(value = "/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping(value = "/drivers/{driverId}/passengers/{passengerId}")
    public ResponseEntity<ReviewResponseDTO> createReview(@PathVariable Long driverId, @PathVariable Long passengerId,
            @RequestBody ReviewRequestDTO dto) {
        ReviewResponseDTO review = reviewService.reviewDriver(driverId, passengerId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }
}
