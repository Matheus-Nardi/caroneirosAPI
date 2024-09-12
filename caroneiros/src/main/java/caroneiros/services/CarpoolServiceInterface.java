package caroneiros.services;



import java.util.List;

import caroneiros.domain.models.Carpool;
import caroneiros.dtos.CarpoolRequestDTO;

public interface CarpoolServiceInterface {

    Carpool saveCarpool(CarpoolRequestDTO carpoolDTO);
    Carpool findCarpoolById(Long id);
    void deleteCarpoolById(Long id);
    Carpool uptadeCarpool(Long id , Carpool carpoolToUpdate);
    List<Carpool> findAvailableCarpools();

}
