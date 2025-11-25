package com.parcial_saber_pro.demo.controller;

import com.parcial_saber_pro.demo.model.Usuario;
import com.parcial_saber_pro.demo.model.Estudiante;
import com.parcial_saber_pro.demo.model.RolUsuario;
import com.parcial_saber_pro.demo.service.AuthService;
import com.parcial_saber_pro.demo.service.EstudianteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final EstudianteService estudianteService;

    public AuthController(AuthService authService, EstudianteService estudianteService) {
        this.authService = authService;
        this.estudianteService = estudianteService;
        this.authService.crearUsuarioCoordinador();
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "auth/login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam(value = "identificador", required = false) String identificador,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        // Usar identificador si viene, si no usar username (para compatibilidad)
        String usuarioLogin = identificador != null ? identificador : username;

        if (usuarioLogin == null || usuarioLogin.trim().isEmpty()) {
            model.addAttribute("error", "Usuario o correo es requerido");
            return "auth/login";
        }

        System.out.println("Intento de login: " + usuarioLogin);

        if (authService.autenticar(usuarioLogin, password)) {
            Optional<?> usuarioOpt = authService.obtenerUsuario(usuarioLogin);

            if (usuarioOpt.isPresent()) {
                Object usuario = usuarioOpt.get();

                if (usuario instanceof Usuario) {
                    Usuario user = (Usuario) usuario;
                    session.setAttribute("usuario", user);
                    session.setAttribute("rol", user.getRol().name());
                    session.setAttribute("username", user.getUsername());

                    System.out.println("Login exitoso como: " + user.getRol());

                    if (user.getRol() == RolUsuario.COORDINADOR) {
                        return "redirect:/";
                    } else {
                        return "redirect:/estudiante/dashboard";
                    }
                } else if (usuario instanceof Estudiante) {
                    Estudiante estudiante = (Estudiante) usuario;
                    session.setAttribute("usuario", estudiante);
                    session.setAttribute("rol", "ESTUDIANTE");
                    session.setAttribute("username", estudiante.getCorreoElectronico());
                    return "redirect:/estudiante/dashboard";
                }
            }
        }

        System.out.println("Login fallido para: " + usuarioLogin);
        model.addAttribute("error", "Usuario o contraseña incorrectos");
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login";
    }
}