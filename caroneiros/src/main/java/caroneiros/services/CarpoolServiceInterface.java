package caroneiros.services;

import java.util.List;

import caroneiros.domain.models.Carpool;
import caroneiros.dtos.CarpoolRequestDTO;
import caroneiros.dtos.CarpoolResponseDTO;

public interface CarpoolServiceInterface {

    CarpoolResponseDTO saveCarpool(CarpoolRequestDTO carpoolDTO);
    Carpool findCarpoolById(Long id);
    void deleteCarpoolById(Long id);
    Carpool uptadeCarpool(Long id , Carpool carpoolToUpdate);
    List<Carpool> findAvailableCarpools();

}
