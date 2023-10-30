package es.aitor.com.aitor.Servicios;

import es.aitor.com.aitor.Entidades.Persona;
import es.aitor.com.aitor.Entidades.Usuario;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UsuariosServices {


    private List<Usuario> usuarios = new ArrayList<>();


    public List<Usuario> findAll() {
        return usuarios;
    }

    // Registar usuarios
    public  void registarUSuario( Usuario usuario){
        usuarios.add(usuario);

    }

    public  Usuario  buscarUsuario(Usuario nombre){

        return usuarios.stream()
                .filter(n-> n.equals(nombre))
                .findFirst()
                .orElse(null);


    }

    public  boolean validarusuario(String name , String password ,String correo ){

        for (Usuario usuario:usuarios   ){

            if (usuario.getNombre().equals(name) && usuario.getContrasena().equals(password) && usuario.getCorreo().equals(correo)){

                return  true ;
            }
        }
        return false;

    }



    @PostConstruct
    public  void init(){

        usuarios.addAll(
                Arrays.asList(
                        Usuario.builder()

                                .nombre("admin")
                                .apellido("admin")
                                .correo("admin")
                                .contrasena("admin")

                                .build()
                ));




    }



}
