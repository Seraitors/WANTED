package es.aitor.com.aitor.repositorios;

import es.aitor.com.aitor.Entidades.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepositorio extends JpaRepository<Roles,Long> {


    public Optional<Roles> findByNombre(String nombre);
}
