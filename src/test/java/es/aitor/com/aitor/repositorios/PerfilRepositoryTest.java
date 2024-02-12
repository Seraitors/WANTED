package es.aitor.com.aitor.repositorios;


import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import es.aitor.com.aitor.Entidades.Arco;
import es.aitor.com.aitor.Entidades.Persona;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@DataJpaTest
@DBRider

@DataSet(value = {"datasets/persona.yml", "datasets/tipo_arco.yml"})
 class PerfilRepositoryTest {

    @Autowired
    private PersonasRepository personasRepository;



    @Autowired
    private ArcoRepository arcoRepository;
    public static final int PAGE_SIZE = 2;


    //derivada
    @Test

    void testConsultasDerivadas() {
        List<Arco> arco = arcoRepository.findByNombre("Wano");
        assertThat(arco).hasSize(1);
    }







    //by example
    @Test
    public void testQueryByExample() {

        Persona ejemploPersona = new Persona();
        ejemploPersona.setNombre("Zoro");


        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.STARTING);

        Example<Persona> example = Example.of(ejemploPersona, matcher);

        List<Persona> resultado = personasRepository.findAll(example);
        System.out.println(resultado);
    }







    //query
    @Test
    @DataSet(value = {"datasets/persona.yml", "datasets/tipo_arco.yml"})
    public void TesDeQuery(){
        List<Persona> personas = personasRepository.findPersonasByPrecio(90);

        assertThat(personas.get(0).getPrecio()).isEqualTo(90);

    }




    //ordenar
    @Test
    @DataSet(value = {"datasets/persona.yml", "datasets/tipo_arco.yml"})
    public  void Ordenar(){
        Sort sort = Sort.by(Sort.Order.asc("nombre"));


        List<Persona> personas = personasRepository.findAllByOrderByNombreAsc(sort);
        assertEquals("Kaido",personas.get(0).getNombre());
    }


    @Test
    @DataSet(value = {"datasets/persona.yml", "datasets/tipo_arco.yml"})
    void should_FindByNombreStartingWithIgnoreCase() {
        // Aquí se asume que tienes una clase PersonaRepository para gestionar entidades Persona
        List<Persona> personas = personasRepository.findByNombreContainsIgnoreCase("l");
        int numFiguras = 1;
        assertThat(personas).hasSize(numFiguras);
    }


    // busca entre dos precios y si lo encuentra te dice qu eets atood bien
    @Test

    void testFindAllWithCriteria_DosFechas() {
        Integer fechaFrom = 90;
        Integer fechaTo = 100;
        List<Persona> personas = personasRepository.findAllWithCriteria(fechaFrom,fechaTo);

        assertThat(personas).extracting(Persona::getId)
                .containsExactlyInAnyOrder(DatasetConstants.Luffy_id, DatasetConstants.Zoro_id);
    }

    @Test
    void testFindAllWithCriteria_Null(){

        Integer fechaFrom = 150;
        Integer fechaTo = 200;
        List<Persona> personas = personasRepository.findAllWithCriteria(fechaFrom,fechaTo);

        assertThat(personas).extracting(Persona::getId)
                .containsExactlyInAnyOrder(DatasetConstants.Sanji_id, DatasetConstants.Kaido_id);

    }

























   /* @Test
    @DataSet(value = {"datasets/persona.yml", "datasets/tipo_arco.yml"})
    void should_findLas3MascotasMasJovenes() {
        Sort sortByFechaNacimiento = Sort.by("fechaNacimiento").descending();
        List<Persona> personas = personasRepository.findTop3By(sortByFechaNacimiento);
        assertThat(personas)
                .extracting(Persona::getId)
                .containsExactlyInAnyOrder(DatasetConstants.MASCOTA_ID_1, DatasetConstants.MASCOTA_ID_2, DatasetConstants.MASCOTA_ID_3);
    }


    @Test
    @DataSet(value = {"datasets/persona.yml", "datasets/tipo_arco.yml"})
    void testFindAllPage() {
        Pageable pageable = PageRequest.of(0, PAGE_SIZE, Sort.Direction.ASC, "nombre");

        Page<Persona> mascotasFirstPage = personasRepository.findPageBy(pageable);

        assertThat(mascotasFirstPage)
                .extracting(Persona::getId)
                .containsExactly(DatasetConstants.BRANDY_ID, DatasetConstants.MORTY_ID);

        assertThat(mascotasFirstPage.getTotalElements()).isEqualTo(4);
        assertThat(mascotasFirstPage.getNumber()).isZero();
        assertThat(mascotasFirstPage.getTotalPages()).isEqualTo(2);
        assertThat(mascotasFirstPage.getNumberOfElements()).isEqualTo(PAGE_SIZE);
        assertThat(mascotasFirstPage.getSize()).isEqualTo(2);
    }
    */







}
