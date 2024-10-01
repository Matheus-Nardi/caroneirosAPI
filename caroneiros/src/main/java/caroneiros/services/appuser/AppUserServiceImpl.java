package caroneiros.services.appuser;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import caroneiros.domain.models.AppUser;
import caroneiros.dtos.appuser.AppUserDTO;
import caroneiros.dtos.appuser.AppUserResetPasswordDTO;
import caroneiros.dtos.appuser.AppUserResponseDTO;
import caroneiros.dtos.appuser.AppUserToUpdateRequestDTO;
import caroneiros.dtos.mapper.AppUserMapper;
import caroneiros.infra.exceptions.InvalidOperationException;
import caroneiros.infra.exceptions.NotFoundException;
import caroneiros.repositories.AppUserRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AppUserResponseDTO saveUser(AppUserDTO user) {
        log.info("Salvando usuário [{}]", user.name());
        AppUser userToSave = new AppUser(user);
        userToSave.setPassword(passwordEncoder.encode(userToSave.getPassword()));
        userRepository.save(userToSave);
        return AppUserMapper.toAppUserResponseDTO(userToSave);
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

    @Override
    public List<AppUserResponseDTO> saveAll(List<AppUserDTO> users) {
        List<AppUser> usersSaved = users.stream().map(user -> new AppUser(user)).toList();

        userRepository.saveAll(usersSaved);
        return usersSaved.stream().map(user -> AppUserMapper.toAppUserResponseDTO(user)).collect(Collectors.toList());
    }

    @Override
    public void updateUser(Long id, AppUser user) {
        AppUser userFromDB = getUserByIdOrThrow(id);
        userFromDB.setScore(user.getScore());
        userRepository.save(userFromDB);
    }

    @Transactional
    @Override
    public void resetPassword(Long id, AppUserResetPasswordDTO dto) {
        log.info("Resetando senha do usuario [{}]", id);
        AppUser userFromDB = getUserByIdOrThrow(id);

        if (!dto.newPassword().equals(dto.confirmedNewPassword()))
            throw new InvalidOperationException("As senhas precisam ser iguais");

        if (passwordEncoder.matches(dto.newPassword(), userFromDB.getPassword()))
            throw new InvalidOperationException("A nova senha não pode ser igual a antiga senha");

        userFromDB.setPassword(passwordEncoder.encode(dto.newPassword()));
        userRepository.save(userFromDB);
    }

}
