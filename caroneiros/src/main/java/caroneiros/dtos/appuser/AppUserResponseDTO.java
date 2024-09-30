package caroneiros.dtos.appuser;

public record AppUserResponseDTO(Long id, String name, String email, String phone, String bio, Boolean driver,
        Double score) {

}
