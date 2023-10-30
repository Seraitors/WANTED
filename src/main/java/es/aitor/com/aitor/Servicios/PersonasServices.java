package es.aitor.com.aitor.Servicios;

import es.aitor.com.aitor.Entidades.Arco;
import es.aitor.com.aitor.Entidades.Persona;
import es.aitor.com.aitor.repositorios.PersonasRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

   public  List<Persona> findByNombre(String filtro){

       return repositorio.findByNombreContainsIgnoreCase(unaccent(filtro));
   }




}
