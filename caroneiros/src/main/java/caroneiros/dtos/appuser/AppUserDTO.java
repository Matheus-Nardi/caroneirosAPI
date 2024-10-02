package caroneiros.dtos.appuser;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AppUserDTO(
        @NotEmpty(message = "{field.name.mandatory}") String name,
        @NotEmpty(message = "{field.email.mandatory}") @Email(message = "{field.email.invalid}") String email,
        @NotEmpty(message = "{field.password.mandatory}")
        @Size(min = 8, message = "{field.password.size}")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
        message = "{field.password.invalid}")
        String password,
        @CPF @NotEmpty(message = "{field.cpf.mandatory}") String cpf,
        @NotEmpty(message = "{field.phone.mandatory}") @Size(min = 11, max = 11, message = "{field.phone.size}") String phone,
        String bio,
        Boolean driver) {
}
