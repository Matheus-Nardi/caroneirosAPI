package caroneiros.dtos.appuser;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record AppUserToUpdateRequestDTO(
        
        @NotEmpty(message = "{field.name.mandatory}") 
        String name,
        String bio,
        @Size(min = 11, max = 11) 
        @NotEmpty(message = "{field.phone.mandatory}")
        String phone, 
        Boolean driver) {

}
