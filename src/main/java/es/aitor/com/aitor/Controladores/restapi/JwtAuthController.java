package es.aitor.com.aitor.Controladores.restapi;


import es.aitor.com.aitor.Entidades.Registrar;
import es.aitor.com.aitor.Entidades.Roles;
import es.aitor.com.aitor.Servicios.RegistrarService;
import es.aitor.com.aitor.dto.jwt.*;
import es.aitor.com.aitor.seguridad.jwt.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class JwtAuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider tokenProvider;
  private final UserDetailsService userDetailsService;
  private final RegistrarService registrarService;
  private final JwtMapper mapper;

  @PostMapping(value = "/signin")
  public ResponseEntity<?> authenticate(@Valid @RequestBody JwtSigninRequest dto) throws Exception {

    try{
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        dto.getUsername(), dto.getPassword()));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }

    UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
    String token = tokenProvider.generateToken(userDetails);
    Set<Roles> roles = userDetails.getAuthorities().stream()
      .map(item -> Roles.builder().nombre(item.getAuthority()).build())
      .collect(Collectors.toSet());

    return ResponseEntity.ok(JwtSigninResponse.builder()
      .token(token)
      .username(userDetails.getUsername())
      .roles(roles)
      .build());
  }

  @PostMapping("/signup")
  public ResponseEntity<?> register(@Valid @RequestBody JwtSignupRequest dto) throws Exception {
    if (dto == null)
      return ResponseEntity.badRequest().body(null);
    try {
      Registrar registrar = registrarService.save(mapper.toEntity(dto));
      JwtSignupResponse signupResponse = mapper.toDto(registrar);
      URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
          .buildAndExpand(registrar.getId()).toUri();
      return ResponseEntity.created(location).body(signupResponse);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ResponseEntity.internalServerError()
          .body(Collections.singletonMap("error", e.getMessage()));
    }
  }

}
