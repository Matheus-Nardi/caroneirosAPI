package caroneiros.domain.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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


@Data
@Entity
@Builder
@AllArgsConstructor
public class Carpool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "driver_id" , nullable = false)
    private AppUser driver;

    @OneToMany(mappedBy = "carpool" , orphanRemoval = true)
    private List<CarpoolReservation> reservations;
    
    @Column(nullable = false)
    @NotNull(message = "Set a estimated departure")
    private LocalDateTime estimatedDeparture;

    private LocalDateTime estimatedArrival;
    private Double estimadedPrice;

    @Column(nullable = false)
    @NotNull(message = "The filed seats is mandatory")
    @Min(1)
    @Max(4)
    private Integer seatsAvailable;

    public Carpool() {
        reservations = new ArrayList<>();
    }

}