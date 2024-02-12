package es.aitor.com.aitor.Servicios;


import es.aitor.com.aitor.Entidades.Registrar;
import es.aitor.com.aitor.dto.Usuario.JpaMapper;
import es.aitor.com.aitor.dto.Usuario.UsuarioSignupDto;
import es.aitor.com.aitor.repositorios.RegistratRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service

public class RegistrarService {


    private final RegistratRepositorio repositorio;



    private final PasswordEncoder passwordEncoder;

    private final JpaMapper mapper;



    public Registrar save(Registrar u) {
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        return repositorio.save(u);
    }

    public Registrar save(UsuarioSignupDto dto) {
        Registrar registrar = mapper.toEntity(dto);
        registrar.setPassword(passwordEncoder.encode(dto.password()));
        return repositorio.save(mapper.toEntity(dto));
    }



    public List<Registrar> saveAll (List<Registrar> lista) { return repositorio.saveAll(lista); }

    public Registrar findById(long id) {
        return repositorio.findById(id).orElse(null);
    }


    public Registrar findByUsernameOrEmail(String username, String email) {
        return repositorio.findByUsernameOrEmail(username,email).orElse(null);
    }

}
