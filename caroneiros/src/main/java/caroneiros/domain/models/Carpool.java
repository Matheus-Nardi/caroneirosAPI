package caroneiros.domain.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Carpool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private AppUser driver;

    @OneToMany(mappedBy = "carpool", orphanRemoval = true)
    private List<CarpoolReservation> reservations;

    @Column(nullable = false)
    @NotNull(message = "Set a estimated departure")
    private LocalDateTime estimatedDeparture;

    private LocalDateTime estimatedArrival;
    private Double estimatedPrice;

    @Enumerated(EnumType.STRING)
    private City departureCity;

    @Enumerated(EnumType.STRING)
    private City arrivalCity;

    @Column(nullable = false)
    @NotNull(message = "The filed seats is mandatory")
    @Min(1)
    @Max(4)
    private Integer seatsAvailable;

    public void addReservation(CarpoolReservation carpoolReservation) {
        if (this.reservations == null) {
            this.reservations = new ArrayList<>();
        }
        this.reservations.add(carpoolReservation);
        carpoolReservation.setCarpool(this);
    }

    public boolean hasAvailableSeats() {
        return this.getSeatsAvailable() > 0;
    }

}