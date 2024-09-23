package caroneiros.services.appuser;

import java.util.List;

import caroneiros.domain.models.AppUser;
import caroneiros.dtos.appuser.AppUserDTO;
import caroneiros.dtos.appuser.AppUserResponseDTO;
import caroneiros.dtos.appuser.AppUserToUpdateRequestDTO;

public interface AppUserService {

    AppUserResponseDTO saveUser(AppUserDTO user);

    //Metodo para fins de facilidade
    List<AppUserResponseDTO> saveAll(List<AppUserDTO> users);

    AppUser findUserById(Long id);

    void deleteUserById(Long id);

    AppUser updateUserById(Long id, AppUserToUpdateRequestDTO userToUpdate);

    void updateUser(Long id, AppUser user);



}
