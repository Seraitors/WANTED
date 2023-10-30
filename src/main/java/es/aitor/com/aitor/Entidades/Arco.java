package es.aitor.com.aitor.Entidades;


import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tipo_arco")
public class Arco {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true, length=50)
    private String nombre;



}
