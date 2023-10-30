package es.aitor.com.aitor.Entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Persona")
public class Persona {
    @Id @GeneratedValue
    //@Min(value=1, message = "{mascota.id.mayorquecero}")
    private  Long id;
    @Column(nullable = false,unique = true,length = 50)
    @NotNull( message = "el tipo debe ir rellenado")
    private String url;

    @NotNull( message = "el tipo debe ir rellenado")
    private  String nombre;
    // @NotNull( message = "el tipo debe ir rellenado")

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "tipo_arco_id")

    private  Arco arco;

    private int precio;
    private  String des;
}
