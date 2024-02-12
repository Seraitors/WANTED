package es.aitor.com.aitor.repositorios;

import es.aitor.com.aitor.Entidades.Persona;
import es.aitor.com.aitor.repositorios.custom.PersonaCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonasRepository extends JpaRepository<Persona,Long> , PersonaCustomRepository {

    public List<Persona> findByNombreStartingWithIgnoreCase (String nombre);

    public  List<Persona> findByNombreContainsIgnoreCase(String s);
    List<Persona> findByNombre(String nombre);
    List<Persona>findPersonasByPrecioGreaterThan(int precio);


    @Query("SELECT p FROM Persona p WHERE p.precio = :precio")
    List<Persona> findPersonasByPrecio(@Param("precio") int precio);


    List<Persona> findAllByOrderByNombreAsc(Sort sort);
    public List<Persona> findTop3By(Sort sort);

    Page<Persona> findPageBy(Pageable pageable);



/*
    @Query("SELECT p FROM Persona p WHERE p.des = :des")
    List<Persona> findByDescripcion(@Param("descripcion") String descripcion);

    @Query("SELECT p FROM Persona p WHERE p.precio > :precio")
    List<Persona> findByPrecioMayor(@Param("precio") double precio);
*/


}
