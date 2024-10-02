package caroneiros.dtos.appuser;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AppUserResetPasswordDTO(
        @NotEmpty(message = "{field.newPassword.mandatory}") 
        @Size(min = 8, message = "{field.password.size}")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
          message = "{field.password.invalid}") String newPassword,


        @NotEmpty(message = "{field.confirmedPassword.mandatory}") 
        @Size(min = 8, message = "{field.password.size}")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{8,}$",
        message = "{field.password.invalid}") 
        String confirmedNewPassword) {

}
