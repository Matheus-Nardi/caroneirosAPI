package caroneiros.dtos.mapper;

import caroneiros.domain.models.AppUser;
import caroneiros.dtos.AppUserResponseDTO;

public class AppUserMapper {
    public static AppUserResponseDTO toAppUserResponseDTO(AppUser user) {
        return new AppUserResponseDTO(user.getName(), user.getPhone(), user.getBio(), user.isDriver());
    }
}
