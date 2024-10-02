package caroneiros.services.appuser;

import caroneiros.domain.models.AppUser;
import caroneiros.dtos.appuser.AppUserDTO;
import caroneiros.dtos.appuser.AppUserResetPasswordDTO;
import caroneiros.dtos.appuser.AppUserResponseDTO;
import caroneiros.dtos.appuser.AppUserToUpdateRequestDTO;

public interface AppUserService {

    AppUserResponseDTO saveUser(AppUserDTO user);

    AppUser findUserById(Long id);

    void deleteUserById(Long id);

    AppUser updateUserById(Long id, AppUserToUpdateRequestDTO userToUpdate);

    void updateUser(Long id, AppUser user);

    void resetPassword(Long id, AppUserResetPasswordDTO dto);

}
