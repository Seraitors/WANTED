package es.aitor.com.aitor.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtSignupResponse {
  private Long id;
  private String username;
  private LocalDateTime fechaAlta;
}
