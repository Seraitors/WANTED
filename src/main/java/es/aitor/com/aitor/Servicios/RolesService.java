package es.aitor.com.aitor.Servicios;


import es.aitor.com.aitor.Entidades.Roles;
import es.aitor.com.aitor.repositorios.RolesRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class RolesService   {


    private final RolesRepositorio rolesRepositorio;

    public List<Roles> findAll() {
        return rolesRepositorio.findAll();
    }
    public Optional<Roles> findByNombre(String nombre) { return rolesRepositorio.findByNombre(nombre);}

    public Roles save(Roles p) { return rolesRepositorio.save(p); }



}
