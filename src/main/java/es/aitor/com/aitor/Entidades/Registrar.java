package es.aitor.com.aitor.Entidades;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;




@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)

@Table(name = "Registrar")
public class Registrar {




    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;
    private String password;

    @CreatedDate
    private LocalDateTime fechaAlta;

    //El ManyToMany se utiliza para hacer una relacion de muchos a muchos enntre dos entidades
    //Lo que estoy haciendo aqui es establecer una relacion de muchos a muchos entre las entidades Usuario y Perfil
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "roles_perfil",
            joinColumns = @JoinColumn(
                    name = "registar_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "roles_id", referencedColumnName = "id"))
    private Set<Roles> roles = new HashSet<>();


}
