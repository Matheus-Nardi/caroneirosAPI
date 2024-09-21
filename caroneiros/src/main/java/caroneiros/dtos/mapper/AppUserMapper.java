package caroneiros.dtos.mapper;

import caroneiros.domain.models.AppUser;
import caroneiros.dtos.appuser.AppUserResponseDTO;

public class AppUserMapper {
    public static AppUserResponseDTO toAppUserResponseDTO(AppUser user) {
        Double score = user.getScore() == null ? 0.0 : user.getScore();
        String formattedScore = String.format("%.2f", score);
        return new AppUserResponseDTO(user.getId(), user.getName(), user.getPhone(), user.getBio(), user.isDriver(),
                Double.parseDouble(formattedScore));
    }
}
