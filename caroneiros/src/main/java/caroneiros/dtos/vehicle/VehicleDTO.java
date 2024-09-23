package caroneiros.dtos.vehicle;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record VehicleDTO(
        @Pattern(regexp = "[A-Z]{3}[0-9]{4}|[A-Z]{3}[0-9][A-Z][0-9]{2}", message = "Invalid license plate format") String licensePlate,
        @NotEmpty(message = "The filed model is mandatory") 
         String model,
        @NotEmpty(message = "The field color is mandatory") 
         String color) {

}
