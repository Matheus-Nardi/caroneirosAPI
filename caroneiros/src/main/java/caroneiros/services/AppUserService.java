package caroneiros.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import caroneiros.domain.models.AppUser;
import caroneiros.domain.models.Vehicle;
import caroneiros.domain.repositories.AppUserRepository;
import caroneiros.dtos.AppUserResponseDTO;
import caroneiros.dtos.AppUserToUpdateRequestDTO;
import caroneiros.dtos.RegisterVehicleRequestDTO;
import caroneiros.infra.exceptions.DontDriverException;
import caroneiros.infra.exceptions.NotFoundException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AppUserService {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private VehicleService vehicleService;

    public AppUserResponseDTO saveUser(AppUser user) {
        log.info("Salvando usuário [{}]", user.getName());
        userRepository.save(user);
        return toAppUserResponseDTO(user);
    }

    public AppUserResponseDTO toAppUserResponseDTO(AppUser user) {
        return new AppUserResponseDTO(user.getName(), user.getPhone(), user.getBio(), user.isDriver());
    }

    public AppUser findUserById(long id) {
        log.info("Buscando usuário de id [{}]", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Não foi possível encontrar um usuário de id " + id));
    }

    @Transactional
    public void deleteUserById(Long id) {
        log.info("Deletando usuário de id [{}]", id);
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Não foi possível encontrar um usuário de id " + id));

        userRepository.delete(user);
    }

    @Transactional
    public AppUser updateUserById(Long id, AppUserToUpdateRequestDTO userToUpdate) {
        log.info("Atualizando usuário de id [{}]", id);
        AppUser user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Não foi possível encontrar um usuário de id " + id));

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

    public void registerVehicle(RegisterVehicleRequestDTO registerVehicleRequestDTO) {
        log.info("Registrando veículo [{}] para o usúario [{}]", registerVehicleRequestDTO.model(),
                registerVehicleRequestDTO.driverId());
        AppUser user = userRepository.findById(registerVehicleRequestDTO.driverId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        Vehicle vehicle = Vehicle.builder()
                .driver(user)
                .color(registerVehicleRequestDTO.color())
                .model(registerVehicleRequestDTO.model())
                .licensePlate(registerVehicleRequestDTO.licensePlate())
                .build();
                
        if (user.isDriver()) {
            vehicleService.addVehicleForUser(user, vehicle);
        } else {
            throw new DontDriverException("O usuário informado não é um motorista");
        }
    }

}
