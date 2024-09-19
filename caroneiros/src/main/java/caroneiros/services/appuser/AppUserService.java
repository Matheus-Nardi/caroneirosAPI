package caroneiros.services.appuser;

import caroneiros.domain.models.AppUser;
import caroneiros.dtos.appuser.AppUserResponseDTO;
import caroneiros.dtos.appuser.AppUserToUpdateRequestDTO;

public interface AppUserService {

    AppUserResponseDTO saveUser(AppUser user);

    AppUser findUserById(Long id);

    void deleteUserById(Long id);

    AppUser updateUserById(Long id, AppUserToUpdateRequestDTO userToUpdate);

}
