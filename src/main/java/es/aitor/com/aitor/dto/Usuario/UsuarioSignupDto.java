package es.aitor.com.aitor.dto.Usuario;

import es.aitor.com.aitor.Entidades.Roles;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Builder
public record UsuarioSignupDto (

  @NotNull
   String username,

  @NotNull
   String email,

  @NotNull
   String password,

   Set<Roles> roles


){}