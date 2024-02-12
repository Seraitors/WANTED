package es.aitor.com.aitor.dto.Usuario;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public record PerfilSignupDto (

  Long id,
  @NotNull
  String nombre
  ) {}
