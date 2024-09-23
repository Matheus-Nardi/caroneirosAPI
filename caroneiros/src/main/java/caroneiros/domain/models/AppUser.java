package caroneiros.domain.models;

import java.util.ArrayList;
import java.util.List;

import caroneiros.dtos.appuser.AppUserDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@AllArgsConstructor
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)

    private String name;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, length = 11)
    private String phone;

    private String bio;

    private Double score;

    @Column(nullable = false)
    private boolean driver;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    private List<Vehicle> vehicles;

    public AppUser() {
        this.vehicles = new ArrayList<>();
    }

    public AppUser(AppUserDTO dto) {
        this.name = dto.name();
        this.cpf = dto.cpf();
        this.phone = dto.phone();
        this.bio = dto.bio();
        this.driver = dto.driver();
    }

}