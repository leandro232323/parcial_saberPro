package com.parcial_saber_pro.demo.controller;

import com.parcial_saber_pro.demo.model.Estudiante;
import com.parcial_saber_pro.demo.model.Usuario;
import com.parcial_saber_pro.demo.model.ResultadoSaberPro;
import com.parcial_saber_pro.demo.service.EstudianteService;
import com.parcial_saber_pro.demo.service.ResultadoSaberProService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/estudiante")
public class EstudianteViewController {
    
    @Autowired
    private EstudianteService estudianteService;
    
    @Autowired
    private ResultadoSaberProService resultadoService;
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        // Verificar que el usuario esté autenticado y sea estudiante
        Object usuarioObj = session.getAttribute("usuario");
        
        if (usuarioObj == null) {
            return "redirect:/auth/login";
        }
        
        Long estudianteId = null;
        
        // Obtener el ID del estudiante según el tipo de usuario
        if (usuarioObj instanceof Usuario) {
            Usuario usuario = (Usuario) usuarioObj;
            estudianteId = usuario.getEstudianteId();
        } else if (usuarioObj instanceof Estudiante) {
            Estudiante estudiante = (Estudiante) usuarioObj;
            estudianteId = estudiante.getId();
        }
        
        if (estudianteId == null) {
            return "redirect:/auth/login?error=student_not_found";
        }
        
        // Obtener datos completos del estudiante con sus resultados
        Estudiante estudiante = estudianteService.findByIdWithResultados(estudianteId);
        
        if (estudiante == null) {
            return "redirect:/auth/login?error=student_not_found";
        }
        
        // Obtener todos los resultados del estudiante
        List<ResultadoSaberPro> resultados = resultadoService.findByEstudianteId(estudianteId);
        
        // DEBUG: Verificar qué resultados se están cargando
        System.out.println("=== DEBUG ESTUDIANTE DASHBOARD ===");
        System.out.println("Estudiante: " + estudiante.getPrimerNombre() + " " + estudiante.getPrimerApellido());
        System.out.println("ID Estudiante: " + estudianteId);
        System.out.println("Resultados encontrados: " + resultados.size());
        
        for (ResultadoSaberPro resultado : resultados) {
            System.out.println("Resultado ID: " + resultado.getId());
            System.out.println("Puntaje Global: " + resultado.getPuntajeGlobal());
            System.out.println("Comunicación Escrita: " + resultado.getComunicacionEscrita());
            System.out.println("Razonamiento Cuantitativo: " + resultado.getRazonamientoCuantitativo());
            System.out.println("Lectura Crítica: " + resultado.getLecturaCritica());
            System.out.println("Competencias Ciudadanas: " + resultado.getCompetenciasCiudadanas());
            System.out.println("Inglés: " + resultado.getIngles());
            System.out.println("---");
        }
        System.out.println("=== FIN DEBUG ===");
        
        // Agregar datos al modelo
        model.addAttribute("estudiante", estudiante);
        model.addAttribute("resultados", resultados);
        model.addAttribute("nombreCompleto", estudiante.getNombreCompleto());
        
        return "estudiante/dashboard";
    }
}