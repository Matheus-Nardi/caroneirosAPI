package caroneiros.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import caroneiros.domain.models.AppUser;
import caroneiros.dtos.appuser.AppUserDTO;
import caroneiros.dtos.appuser.AppUserResetPasswordDTO;
import caroneiros.dtos.appuser.AppUserResponseDTO;
import caroneiros.dtos.appuser.AppUserToUpdateRequestDTO;
import caroneiros.dtos.mapper.AppUserMapper;
import caroneiros.services.appuser.AppUserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(value = "/users")
public class AppUserController {

    @Autowired
    private AppUserService userService;

    @PostMapping
    public ResponseEntity<AppUserResponseDTO> saveUser(@Valid @RequestBody AppUserDTO appUser) {
        log.info("Requisição do tipo POST para baseUrl/users");
        AppUserResponseDTO userSaved = userService.saveUser(appUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
    }

    @PutMapping(value = "{id}/recover")
    public ResponseEntity<Void> forgotPassword(@PathVariable Long id, @RequestBody AppUserResetPasswordDTO dto) {
        log.info("Requisição do tipo PUT para baseUrl/users{}/recover", id);
        userService.resetPassword(id, dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AppUserResponseDTO> findUserById(@PathVariable Long id) {
        log.info("Requisição do tipo GET para baseURL/users/{}", id);
        AppUser userFound = userService.findUserById(id);
        return ResponseEntity.ok().body(AppUserMapper.toAppUserResponseDTO(userFound));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<AppUserResponseDTO> updateUser(@PathVariable Long id,
            @Valid @RequestBody AppUserToUpdateRequestDTO userToUpdate) {
        log.info("Requisição do tipo PATCH para baseURL/users/{}", id);
        userService.updateUserById(id, userToUpdate);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Requisição do tipo DELETE para baseURL/users/{}", id);
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
