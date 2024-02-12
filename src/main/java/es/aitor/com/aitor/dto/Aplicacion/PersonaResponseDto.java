package es.aitor.com.aitor.dto.Aplicacion;


import es.aitor.com.aitor.Entidades.Arco;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonaResponseDto {

    private  Long id;


    private String url;


    private  String nombre;

    private Arco arco;

    private int precio;
    private  String des;
}
