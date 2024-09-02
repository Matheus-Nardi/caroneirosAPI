package caroneiros.domain.models;



import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "The field name is mandatory")
    private String name;

    @Column(nullable = false , unique = true)
    @CPF
    @NotEmpty(message = "The filed CPF is mandatory")
    private String cpf;

    @Column(nullable = false)
    @NotEmpty(message = "The field phone is mandatory")
    private String phone;

    private String bio;

    @Column(nullable = false)
    private boolean driver;

    @OneToMany(mappedBy = "driver" , cascade = CascadeType.ALL)
    private List<Vehicle> vehicles;

}