package caroneiros.dtos.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ReviewRequestDTO(
                @NotNull(message = "The filed score is mandatory") @Min(1) @Max(5) Integer score,
                String description) {

}
