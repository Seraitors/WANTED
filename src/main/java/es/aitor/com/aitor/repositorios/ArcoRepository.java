package es.aitor.com.aitor.repositorios;

import es.aitor.com.aitor.Entidades.Arco;
import es.aitor.com.aitor.Entidades.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArcoRepository extends JpaRepository<Arco,Long> {
    Optional<Arco> findByNombre(String nombre);




}
