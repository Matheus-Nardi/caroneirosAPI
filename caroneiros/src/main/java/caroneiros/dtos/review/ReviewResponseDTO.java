package caroneiros.dtos.review;

public record ReviewResponseDTO(
        Long reviewId,
        Long driverId,
        Long passengerId,
        Integer score,
        String description) {

}
