package es.aitor.com.aitor.repositorios;

import es.aitor.com.aitor.Entidades.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonasRepository extends JpaRepository<Persona,Long> {

    public List<Persona> findByNombreStartingWithIgnoreCase (String nombre);

    public  List<Persona> findByNombreContainsIgnoreCase(String filtro);
    List<Persona> findByNombre(String nombre);
}
