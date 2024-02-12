package es.aitor.com.aitor.Controladores;


import es.aitor.com.aitor.Entidades.Registrar;
import es.aitor.com.aitor.Servicios.RegistrarService;
import es.aitor.com.aitor.Servicios.RolesService;
import es.aitor.com.aitor.dto.Usuario.PerfilSignupDto;
import es.aitor.com.aitor.dto.Usuario.UsuarioSignupDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/")

public class InicioSesion {


    //registar son los usuarios
    private final RegistrarService registrarService;


    //roles son los perfiles

    private final RolesService rolesService;


    //revisar las ruras y los retunrs

    @GetMapping("/usuario/login")
    public String login(){
        return "usuario/login";
    }

    //esto lo puse yo para que me cargue mi pagina
    @PostMapping("/usuario/entrar")
    public  String entar(){

        return "redirect:/figuras/lista";
    }

    @GetMapping("/usuario/logout")
    public String logout(){
        return "redirect:/indexSeguridad";
    }


    // Roles son los perfiles cambiamos por qu e tenemso nuevas cosas en el dto
    // con esto cuando iniciamos sesion ya funciona el admin de resgistrarte
    @ModelAttribute("listaPerfiles")
    public List<PerfilSignupDto> listaPerfiles() {
        return rolesService.findAllDto();
    }

    @GetMapping("/usuario/signup")
    public String signup(Model model){
        model.addAttribute("usuarioDto", UsuarioSignupDto.builder().build());
        return "usuario/signup";
    }

    @PostMapping("/usuario/signup")
    public String signupSubmit(@Valid @ModelAttribute("usuarioDto") UsuarioSignupDto dto,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            log.info("hay errores en el formulario");
            bindingResult.getFieldErrors()
                    .forEach(e -> log.info("field: " + e.getField() + ", rejected value: " + e.getRejectedValue()));
            return "usuario/login";
            /*return "Añadir";*/
        } else {
            Registrar usuario = registrarService.findByUsernameOrEmail(dto.username(), dto.email());
            if (usuario != null) { // el usuario ya existe
                bindingResult.rejectValue("username", "username.existente",
                        "ya existe un usuario con ese username");
                return "usuario/signup";
            }
            registrarService.save(dto);
            return "redirect:/usuario/login";

        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/usuario/login";
    }




}
