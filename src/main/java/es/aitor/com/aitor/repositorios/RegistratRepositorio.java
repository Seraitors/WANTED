package es.aitor.com.aitor.repositorios;

import es.aitor.com.aitor.Entidades.Registrar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RegistratRepositorio extends JpaRepository<Registrar, Long> {


    Optional<Registrar> findByUsernameOrEmail(String Registrar, String email);

    @Query("select u from Registrar u " +
            "where lower(u.username) = ?1 or lower(u.email) = ?1")
    Optional<Registrar> buscarPorUsernameOEmail(String s);

    @Query("select u from Registrar u " +
            "where lower(u.username) = :cadena or lower(u.email) = :cadena")
    Optional<Registrar> buscarPorUsernameOEmail_args_por_nombre(@Param("cadena") String cadena);
}
