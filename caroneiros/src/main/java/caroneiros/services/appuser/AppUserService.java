package caroneiros.services.appuser;

import java.util.List;

import caroneiros.domain.models.AppUser;
import caroneiros.dtos.appuser.AppUserResponseDTO;
import caroneiros.dtos.appuser.AppUserToUpdateRequestDTO;

public interface AppUserService {

    AppUserResponseDTO saveUser(AppUser user);

    //Metodo para fins de facilidade
    List<AppUserResponseDTO> saveAll(List<AppUser> users);

    AppUser findUserById(Long id);

    void deleteUserById(Long id);

    AppUser updateUserById(Long id, AppUserToUpdateRequestDTO userToUpdate);



}
