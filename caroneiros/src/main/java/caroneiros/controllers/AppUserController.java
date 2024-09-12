package caroneiros.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import caroneiros.domain.models.AppUser;
import caroneiros.dtos.AppUserResponseDTO;
import caroneiros.dtos.AppUserToUpdateRequestDTO;
import caroneiros.dtos.RegisterVehicleRequestDTO;
import caroneiros.services.AppUserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(value = "/users")
public class AppUserController {

    @Autowired
    private AppUserService userService;

    @PostMapping
    public ResponseEntity<AppUserResponseDTO> saveUser(@Valid @RequestBody AppUser appUser) {
        log.info("Requisição do tipo POST para baseUrl/users");
        AppUserResponseDTO userSaved = userService.saveUser(appUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AppUserResponseDTO> findUserById(@PathVariable Long id) {
        log.info("Requisição do tipo GET para baseURL/user/{}", id);
        AppUser userFound = userService.findUserById(id);
        AppUserResponseDTO userDTO = userService.toAppUserResponseDTO(userFound);
        return ResponseEntity.ok().body(userDTO);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<AppUserResponseDTO> updateUser(@PathVariable Long id,
            @RequestBody AppUserToUpdateRequestDTO userToUpdate) {
        log.info("Requisição do tipo PATCH para baseURL/user/{}", id);
        AppUser userUpdated = userService.updateUserById(id, userToUpdate);
        AppUserResponseDTO userDTO = userService.toAppUserResponseDTO(userUpdated);
        return ResponseEntity.ok().body(userDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Requisição do tipo DELETE para baseURL/user/{}", id);
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    // Não sei onde deve ficar , veiculo ou usuario
    @PostMapping(value = "/vehicles")
    public ResponseEntity<RegisterVehicleRequestDTO> registerVehicle( @RequestBody RegisterVehicleRequestDTO registerVehicleRequestDTO){
        log.info("Requisição do tipo POST para baseUrl/users/vehicles");
        userService.registerVehicle(registerVehicleRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerVehicleRequestDTO);

    }
}
