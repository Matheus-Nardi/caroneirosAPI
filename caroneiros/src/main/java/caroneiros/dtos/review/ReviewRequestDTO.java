package caroneiros.dtos.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReviewRequestDTO(
        @NotNull(message = "{field.score.mandatory}") @Min(value = 1, message = "{field.score.min}") @Max(value = 5, message = "{field.score.max}") Integer score,
        String description) {
}
