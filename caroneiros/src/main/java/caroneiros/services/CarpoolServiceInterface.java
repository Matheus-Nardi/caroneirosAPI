package caroneiros.services;



import java.util.List;
import java.time.LocalDateTime;

import caroneiros.domain.models.Carpool;
import caroneiros.dtos.CarpoolRequestDTO;

public interface CarpoolServiceInterface {

    Carpool saveCarpool(CarpoolRequestDTO carpoolDTO);
    Carpool findCarpoolById(Long id);
    void deleteCarpoolById(Long id);
    Carpool uptadeCarpool(Long id , Carpool carpoolToUpdate);
    List<Carpool> findAvailableCarpools();
    boolean isDriverAvailable(Long driverId, LocalDateTime departureTime);
    void completeCarpool(Long id);

}
