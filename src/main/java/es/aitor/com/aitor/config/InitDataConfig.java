package es.aitor.com.aitor.config;

import es.aitor.com.aitor.Entidades.*;
import es.aitor.com.aitor.Servicios.ArcoServices;
import es.aitor.com.aitor.Servicios.PersonasServices;
import es.aitor.com.aitor.Servicios.RegistrarService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@EnableJpaAuditing
@RequiredArgsConstructor
@Configuration
public class InitDataConfig {

    @Autowired
    private PersonasServices personasServices;

    @Autowired
    private ArcoServices arcoServices;



    private final PasswordEncoder passwordEncoder;
    private final RegistrarService registrarService;

    @PostConstruct
    public void initUsuarios() {
        Roles roleUser = Roles.builder().nombre("ROLE_USER").build();
        //perfilUser = perfilService.save(perfilUser);
        Roles perfilAdmin = Roles.builder().nombre("ROLE_ADMIN").build();
        //perfilAdmin = perfilService.save(perfilAdmin);

        Registrar registrar = Registrar.builder()
                .username("user")
                .email("user@canciones.es")
                .password("user")
                .roles(Set.of(roleUser))
                .build();
        registrar.setPassword(passwordEncoder.encode(registrar.getPassword()));

        //usuario1 = usuarioService.save(usuario1);

        Registrar registrar2 = Registrar.builder()
                .username("admin")
                .email("admin@canciones.es")
                .password("admin")
                .roles(Set.of(roleUser, perfilAdmin))
                .build();
        registrar2.setPassword(passwordEncoder.encode(registrar2.getPassword()));

        //usuario2 = usuarioService.save(usuario2);
        registrarService.saveAll(Arrays.asList(registrar,registrar2));
    }






    @PostConstruct
    public void initPersona() {
        Arco wano = Arco.builder().nombre("Wano").build();
        Arco marinforce = Arco.builder().nombre("Marinforce").build();
        Arco arabasta = Arco.builder().nombre("Arabasta").build();
        Arco Despertar = Arco.builder().nombre("Despertar").build();

        arcoServices.addAll(Arrays.asList(wano, marinforce, arabasta,Despertar));
        personasServices.addAll(
                Arrays.asList(
                        Persona.builder()

                                .id(1L)
                                .url("/Imagen/descarga.jpeg")
                                .nombre("Luffy")
                                .arco(wano)

                                .precio(90)
                                .des("Figura de Luffy de cuando llegó a Wano")
                                .build(),

                        Persona.builder()
                                .id(2L)
                                .url("/Imagen/zoro.jpeg")
                                .nombre("Zoro")
                                .arco(marinforce)

                                .precio(100)
                                .des("Figura de Zoro cuando se enfrentó al villano de las espadas")
                                .build(),

                        Persona.builder()
                                .id(3L)
                                .url("/Imagen/sanji.jpeg")
                                .nombre("Sanji")
                                .arco(arabasta)

                                .precio(150)
                                .des("Figura de Sanji salida de las termas")
                                .build(),
                        Persona.builder()
                                .id(4L)
                                .url("/Imagen/kaido.jpg")
                                .nombre("Kaido")
                                .arco(Despertar)

                                .precio(100)
                                .des("Figura de Kaido antes de pelear contra Luffy")
                                .build()
                ));

    }



    @Bean
    public Subtipos initSubtipos() {
        List<String> subtipos = Arrays.asList("Wano", "Arabasta", "Marinforce","Despertar");
        return new Subtipos(subtipos);
    }
}
