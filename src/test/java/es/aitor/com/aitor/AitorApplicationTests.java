package es.aitor.com.aitor;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import es.aitor.com.aitor.Entidades.Arco;
import es.aitor.com.aitor.Entidades.Persona;
import es.aitor.com.aitor.repositorios.ArcoRepository;
import es.aitor.com.aitor.repositorios.PersonasRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@DataJpaTest
@DBRider
class AitorApplicationTests {
    @Autowired
    PersonasRepository personasRepository;

    ArcoRepository arcoRepository;


    @DataSet("datasets/personajes.yml")

    @Test
    public void textPersonajes() {

        List<Persona> personaList =personasRepository.findByNombre("Luffy");
        assertEquals(1, personaList.size());



    }



        @Test
        @DataSet("datasets/personajes.yml")
        void shouldFindByNombreStartingWithIgnoreCase() {
            // Aquí se asume que tienes una clase PersonaRepository para gestionar entidades Persona
            List<Persona> personas = personasRepository.findByNombreStartingWithIgnoreCase("LUF");

            int numFiguras = 1;
            assertThat(personas).hasSize(numFiguras);
        }



    @Test
    @DataSet("datasets/personajes.yml")
    void should_findByNombreStartingWithIgnoreCase() {
        Persona p = Persona.builder()
                .nombre("Luffy")  // Aquí se agregó el método nombre
                .precio(80)
                .arco(arcoRepository.findByNombre("wano").orElse(null))
                .des("Figura de Luffy de cuando llegó a Wano").build();
        personasRepository.save(p);
        List<Persona> personas = personasRepository.findByNombreStartingWithIgnoreCase("Luffie");
        int numFigura = 1;
        assertThat(personas).hasSize(numFigura);
    }




    @Test
    @DataSet("datasets/personajes.yml")
    void shouldSaveAndRetrievePersona() {
        // Datos de prueba
        Persona nuevaPersona = Persona.builder()
                .nombre("Luffy")
                .arco(arcoRepository.findByNombre("wano").orElse(null))
                .des("Figura de Luffy de cuando llegó a Wano")
                .precio(90)
                .build();

        // Guardar en la base de datos
        personasRepository.save(nuevaPersona);

        // Recuperar de la base de datos
        List<Persona> personas = personasRepository.findByNombreStartingWithIgnoreCase("Luf");

        // Verificar que la persona se haya guardado y recuperado correctamente
        assertThat(personas).hasSize(1);
        Persona personaRecuperada = personas.get(0);
        assertThat(personaRecuperada.getNombre()).isEqualTo("Luffy");
        assertThat(personaRecuperada.getArco().getNombre()).isEqualTo("wano");
        assertThat(personaRecuperada.getDes()).isEqualTo("Figura de Luffy de cuando llegó a Wano");
        assertThat(personaRecuperada.getPrecio()).isEqualTo(90);
    }


}
