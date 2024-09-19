package caroneiros.dtos.carpool;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import caroneiros.domain.models.City;

public record CarpoolRequestDTO(
        City departureCity,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime estimatedDeparture,
        Double estimadedPrice,
        Integer seatsAvailable) {

}
