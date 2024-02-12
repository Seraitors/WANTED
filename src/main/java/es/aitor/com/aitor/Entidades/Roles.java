package es.aitor.com.aitor.Entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "SuperRoles")
public class Roles {



    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    private String descripcion;

    @ManyToMany(mappedBy = "roles")
    private Set<Registrar> resgistrar;

}
