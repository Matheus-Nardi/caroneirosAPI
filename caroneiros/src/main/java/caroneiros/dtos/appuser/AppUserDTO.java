package caroneiros.dtos.appuser;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AppUserDTO(
        @NotEmpty(message = "The field name is mandatory") String name,
        @NotEmpty(message = "The field email is mandatory") @Email(message = "Invalid email") String email,
        @NotEmpty(message = "The field password is mandatory")
        @Size(min = 8, message = "The password must be at least 8 characters long")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
        message = "The password must contain at least 8 characters, one uppercase letter, one lowercase letter, one number and one special character.")
        String password,
        @CPF @NotEmpty(message = "The filed CPF is mandatory") String cpf,
        @NotEmpty(message = "The field phone is mandatory") @Size(min = 11, max = 11) String phone,
        String bio,
        Boolean driver) {

}
