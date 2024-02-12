package es.aitor.com.aitor.Servicios;

import es.aitor.com.aitor.Entidades.Arco;
import es.aitor.com.aitor.Entidades.Persona;
import es.aitor.com.aitor.repositorios.PersonasRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonasServices {

    private  final PersonasRepository repositorio;




    public List<Persona> findAll() {
        return repositorio.findAll();
    }


    public  Persona add(Persona p){
        repositorio.save(p);
        return p;
    }

    public void addAll(List<Persona> lista) {
        repositorio.saveAll(lista);
    }



    public Optional<Persona> findById(Long id) {
        return repositorio.findById(id);
    }

    public Persona edit(Persona m) {
        return repositorio.save(m);
    }

    public void delete(Persona m) {

        repositorio.delete(m);
    }
    // mirar a ver qu ehace esto
    public static String unaccent(String src) {
        return Normalizer
                .normalize(src, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

    // esto es para que funcione lo de filtrar por qu ecomo estabamos devolviendo una lista necesitabmos una Page para que funcione
   public  Page<Persona> findByNombre(String s){

       List<Persona> list = repositorio.findByNombreContainsIgnoreCase(unaccent(s));
       return new PageImpl<>(list);
   }

    public Page<Persona> findAllPaginated(Pageable pageable) {
        return repositorio.findAll(pageable);
    }


 public  Persona save(Persona p){
        return  repositorio.save(p);

 }

    public void deleteById(Long id) {
        repositorio.deleteById(id);
    }

}
