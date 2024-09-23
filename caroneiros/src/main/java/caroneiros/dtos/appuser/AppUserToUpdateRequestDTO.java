package caroneiros.dtos.appuser;

import jakarta.validation.constraints.Size;

public record AppUserToUpdateRequestDTO(String name, String bio,
        @Size(min = 11, max = 11) String phone, Boolean driver) {

}
