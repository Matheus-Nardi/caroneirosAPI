package caroneiros.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Vehicle;
import caroneiros.domain.repositories.AppUserRepository;
import caroneiros.infra.exception.DontDriverException;
import caroneiros.infra.exception.NotFoundAppUserException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AppUserService {

    @Autowired
    private AppUserRepository userRepository;


    public AppUser saveUser(AppUser user) {
        log.info("Salvando usuário [{}]", user.getName());
        return userRepository.save(user);
    }

    public AppUser findUserById(long id) {
        log.info("Buscando usuário de id [{}]", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundAppUserException("Não foi possível encontrar um usuário de id " + id));
    }

    @Transactional
    public void deleteUserById(Long id) {
        log.info("Deletando usuário de id [{}]", id);
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundAppUserException("Não foi possível encontrar um usuário de id " + id));

        userRepository.delete(user);
    }

    @Transactional
    public AppUser updateUserById(Long id, AppUser userToUpdate) {
        log.info("Atualizando usuário de id [{}]", id);
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundAppUserException("Não foi possível encontrar um usuário de id " + id));

        if (userToUpdate.getName() != null) {
            user.setName(userToUpdate.getName());
        }

        return userRepository.save(user);
    }

    public void registerVehicle(Long userId, Vehicle vehicle) {
        log.info("Registrando veículo [{}] para o usúario [{}]", vehicle.getModel(), userId);
        AppUser user = this.findUserById(userId);

        if (user.isDriver()) {
            List<Vehicle> vehicles = user.getVehicles();
            vehicles.add(vehicle);
            userRepository.save(user);
        }else{
            throw new DontDriverException("O usuário informado não é um motorista");
        }

    }

}
