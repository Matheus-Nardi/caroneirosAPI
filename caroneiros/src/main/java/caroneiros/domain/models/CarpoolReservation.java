package caroneiros.domain.models;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class CarpoolReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "passenger_id" , nullable = false)
    private AppUser passenger;

    @ManyToOne
    @JoinColumn(name = "carpool_id" , nullable = false)
    private Carpool carpool;

    @Enumerated(EnumType.STRING)
    private CarpoolStatus status;
}