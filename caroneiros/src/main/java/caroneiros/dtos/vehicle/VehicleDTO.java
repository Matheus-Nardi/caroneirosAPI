package caroneiros.dtos.vehicle;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record VehicleDTO(
        @Pattern(regexp = "[A-Z]{3}[0-9]{4}|[A-Z]{3}[0-9][A-Z][0-9]{2}", message = "{field.licensePlate.invalid}") String licensePlate,
        @NotEmpty(message = "{field.model.mandatory}") String model,
        @NotEmpty(message = "{field.color.mandatory}") String color
) {
}
