package es.aitor.com.aitor.repositorios;

import es.aitor.com.aitor.Entidades.Arco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArcoRepository extends JpaRepository<Arco,Long> {
    Optional<Arco> findById(Long id);
    List<Arco> findByNombre(String nombreArco);





}
