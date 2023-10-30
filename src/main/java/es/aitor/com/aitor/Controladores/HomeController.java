package es.aitor.com.aitor.Controladores;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Security;
import java.util.Arrays;
import java.util.Optional;


@Controller
@Slf4j
@RequestMapping("/")
public class HomeController {
    private  static  final String CONTADOR_NAME_APP="numVisitasApp";
    private  static  final String CONTADOR_NAME_INDEX="numVisitasIndex";


    @GetMapping({"","/index"})
    public  String bienvenida(HttpServletRequest request, HttpServletResponse response){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)){
            String usuario= authentication.getName();

            HttpSession session =request.getSession();
            boolean primeraVez = (session.getAttribute(CONTADOR_NAME_APP) == null);

            if (primeraVez) {

                // Comprobar si el navegador tenía cookie del usuario
                Optional<Cookie> cookieEncontrada = Arrays.stream(request.getCookies())
                        .filter(cookie -> usuario.equals(cookie.getName()))
                        .findAny();

                // si no existe la cookie el contador de visitas se pone a 1
                int contador = 1;
                if (cookieEncontrada.isEmpty()) {  // si no existe la cookie >> primera visita
                    Cookie cookie = new Cookie(usuario, "1");
                    cookie.setPath("/");
                    cookie.setDomain("localhost");
                    cookie.setMaxAge(7 * 24 * 60 * 60);  // 7 días
                    cookie.setSecure(true);
                    cookie.setHttpOnly(true);

                    response.addCookie(cookie);

                } else { // si existe la cookie se recupera el contador y se le suma 1
                    Cookie cookie = cookieEncontrada.get();
                    contador = Integer.parseInt(cookie.getValue()) + 1;
                    cookie.setValue(String.valueOf(contador));
                    cookie.setMaxAge(7 * 24 * 60 * 60);  // 7 días
                    response.addCookie(cookie);

                }

                // Almacenar en session el contador de visitas a la aplicación
                session.setAttribute(
                        CONTADOR_NAME_APP, contador);
                log.info("id de session: {}", session.getId());

            }

            // Almacenar en session el contador de visitas a la pagina index
            Object contadorIndex = session.getAttribute(CONTADOR_NAME_INDEX);
            session.setAttribute(CONTADOR_NAME_INDEX, (contadorIndex == null) ? 1 : (int)contadorIndex + 1);

        }



        return "indexSeguridad";

    }

 /*   @GetMapping("/login")
    public  String login (){

        log.info("paso login");
        return "loginSeguridad";
    }




    @PostMapping("/logout")
    public String logout() {
        log.info("paso por el cierre");
        return "redirect:/login";
    }*/

}
