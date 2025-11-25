package com.parcial_saber_pro.demo.controller;

import com.parcial_saber_pro.demo.model.Estudiante;
import com.parcial_saber_pro.demo.model.ResultadoSaberPro;
import com.parcial_saber_pro.demo.service.EstudianteService;
import com.parcial_saber_pro.demo.service.ResultadoSaberProService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data")
public class DataLoaderController {
    
    @Autowired
    private EstudianteService estudianteService;
    
    @Autowired
    private ResultadoSaberProService resultadoService;
    
    @PostMapping("/load-sample")
    public String loadSampleData() {
        try {
            // Crear un estudiante de ejemplo
            Estudiante estudiante = new Estudiante();
            estudiante.setTipoDocumento("CC");
            estudiante.setPrimerApellido("BARBOSA");
            estudiante.setPrimerNombre("EJEMPLO");
            estudiante.setNumeroRegistro("EK20183007722");
            estudiante.setCorreoElectronico("ejemplo@uts.edu.co");
            estudiante.setNumeroTelefonico("3001234567");
            
            Estudiante savedEstudiante = estudianteService.save(estudiante);
            
            // Crear resultado de ejemplo
            ResultadoSaberPro resultado = new ResultadoSaberPro();
            resultado.setEstudiante(savedEstudiante);
            resultado.setPuntajeGlobal(200);
            resultado.setNivelGlobal("Nivel 3");
            resultado.setComunicacionEscrita(128);
            resultado.setRazonamientoCuantitativo(182);
            resultado.setLecturaCritica(202);
            resultado.setCompetenciasCiudadanas(206);
            resultado.setIngles(183);
            resultado.setNivelInglesCefr("B1");
            
            resultadoService.save(resultado);
            
            return "Datos de ejemplo cargados exitosamente. Estudiante ID: " + savedEstudiante.getId();
        } catch (Exception e) {
            return "Error al cargar datos: " + e.getMessage();
        }
    }
}