package caroneiros.services.carpool;

import java.util.List;
import java.time.LocalDate;

import caroneiros.domain.models.Carpool;
import caroneiros.dtos.carpool.CarpoolRequestDTO;
import caroneiros.dtos.carpool.CarpoolResponseDTO;
import caroneiros.dtos.carpool.CarpoolUpdateRequestDTO;

public interface CarpoolService {

    CarpoolResponseDTO saveCarpool(Long driverId, Long vehicleId, CarpoolRequestDTO carpoolDTO);

    Carpool saveCarpool(Carpool carpool);

    Carpool findCarpoolById(Long id);

    void deleteCarpoolById(Long id , Long driverId);

    Carpool uptadeCarpool(Long id, Carpool carpoolToUpdate);

    CarpoolResponseDTO updateCarpool(Long id, Long driverId, CarpoolUpdateRequestDTO carpoolUpdateRequestDTO);

    List<CarpoolResponseDTO> filterCarpools(Carpool entityFilter);

    List<CarpoolResponseDTO> findCarpoolsByDate(LocalDate date);

}
