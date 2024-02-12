package es.aitor.com.aitor.dto.jwt;

import es.aitor.com.aitor.Entidades.Roles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtSigninResponse {
  private String token;
  @Builder.Default
  private String tokenType = "Bearer";
  private String username;
  private Set<Roles> roles;
}
