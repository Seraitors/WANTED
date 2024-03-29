package es.aitor.com.aitor.Controladores;

import es.aitor.com.aitor.Entidades.Roles;
import es.aitor.com.aitor.Servicios.PersonasServices;
import es.aitor.com.aitor.Servicios.RolesService;
import es.aitor.com.aitor.dto.Usuario.PerfilSignupDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/perfil")

public class PerfilController {

    // roles son mis perfiles
    private final RolesService rolesService;

    private  final PersonasServices personasServices;

    @GetMapping({"/", "/list"})
    public String listado(Model model) {
        model.addAttribute("listaPerfiles", rolesService.findAll() );
        return "perfil/list";
    }
    @GetMapping("/new")
    public String nuevo(Model model){
        model.addAttribute("perfilDto", new Roles());
        return "perfil/form";
    }

    @ModelAttribute("listaPerfiles")
    public List<PerfilSignupDto> listaPerfiles() {
        return rolesService.findAllDto();
    }

    @PostMapping("/new")
    public String nuevoSubmit(@Valid @ModelAttribute("perfilDto") Roles nuevoPerfil,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            log.info("hay errores en el formulario");
            bindingResult.getFieldErrors()
                    .forEach(e -> log.info("field: " + e.getField() + ", rejected value: " + e.getRejectedValue()));
            return "perfil/form";
        } else {
            Optional<Roles> perfil = rolesService.findByNombre(nuevoPerfil.getNombre());
            if (perfil.isPresent()) { // el perfil ya existe
                bindingResult.rejectValue("nombre", "nombre.existente",
                        "ya existe un perfil con ese nombre");
                return "perfil/form";
            }
            rolesService.save(nuevoPerfil);
            return "redirect:/perfil/list";

        }
    }


}
