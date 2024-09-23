package caroneiros.dtos.appuser;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record AppUserDTO(
        @NotEmpty(message = "The field name is mandatory") 
        String name,
        @CPF
        @NotEmpty(message = "The filed CPF is mandatory")
        String cpf, 
        @NotEmpty(message = "The field phone is mandatory")
        @Size(min = 11 , max = 11)
        String phone, 
        String bio, 
        Boolean driver) {

}
