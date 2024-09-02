package caroneiros.domain.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
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
    @NotEmpty(message = "Set a estimated departure")
    private LocalDateTime estimatedDeparture;

    private LocalDateTime estimatedArrival;
    private Double estimadedPrice;

    @Column(nullable = false)
    @NotEmpty(message = "The filed seats is mandatory")
    private Integer seatsAvailable;

}