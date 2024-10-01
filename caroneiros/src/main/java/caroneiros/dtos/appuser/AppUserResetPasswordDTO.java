package caroneiros.dtos.appuser;

public record AppUserResetPasswordDTO(
    String newPassword,
    String confirmedNewPassword
) {

}
