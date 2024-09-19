package caroneiros.services.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import caroneiros.domain.models.AppUser;
import caroneiros.dtos.appuser.AppUserResponseDTO;
import caroneiros.dtos.appuser.AppUserToUpdateRequestDTO;
import caroneiros.dtos.mapper.AppUserMapper;
import caroneiros.infra.exceptions.NotFoundException;
import caroneiros.repositories.AppUserRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserRepository userRepository;

    @Override
    @Transactional
    public AppUserResponseDTO saveUser(AppUser user) {
        log.info("Salvando usuário [{}]", user.getName());
        userRepository.save(user);
        return AppUserMapper.toAppUserResponseDTO(user);
    }

    @Override
    public AppUser findUserById(Long id) {
        log.info("Buscando usuário de id [{}]", id);
        return getUserByIdOrThrow(id);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        log.info("Deletando usuário de id [{}]", id);
        AppUser user = getUserByIdOrThrow(id);

        userRepository.delete(user);
    }

    @Override
    @Transactional
    public AppUser updateUserById(Long id, AppUserToUpdateRequestDTO userToUpdate) {
        log.info("Atualizando usuário de id [{}]", id);
        AppUser user = getUserByIdOrThrow(id);

        if (userToUpdate.name() != null) {
            user.setName(userToUpdate.name());
        }
        if (userToUpdate.phone() != null) {
            user.setPhone(userToUpdate.phone());
        }
        if (userToUpdate.bio() != null) {
            user.setBio(userToUpdate.bio());
        }
        if (userToUpdate.driver() != null) {
            user.setDriver(userToUpdate.driver());
        }
        return userRepository.save(user);
    }

    private AppUser getUserByIdOrThrow(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Não foi possível encontrar um usuário de id " + id));
    }

}
