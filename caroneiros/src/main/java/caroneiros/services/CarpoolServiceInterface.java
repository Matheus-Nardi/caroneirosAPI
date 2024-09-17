package caroneiros.services;

import java.util.List;

import caroneiros.domain.models.Carpool;
import caroneiros.dtos.CarpoolRequestDTO;
import caroneiros.dtos.CarpoolResponseDTO;
import caroneiros.dtos.CarpoolUpdateRequestDTO;

public interface CarpoolServiceInterface {

    CarpoolResponseDTO saveCarpool(Long driverId, Long vehicleId, CarpoolRequestDTO carpoolDTO);

    Carpool saveCarpool(Carpool carpool);

    Carpool findCarpoolById(Long id);

    void deleteCarpoolById(Long id);

    Carpool uptadeCarpool(Long id, Carpool carpoolToUpdate);

    Carpool updateCarpool(Long id, CarpoolUpdateRequestDTO carpoolUpdateRequestDTO);

    List<CarpoolResponseDTO> findAvailableCarpools();

}
