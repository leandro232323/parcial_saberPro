package com.parcial_saber_pro.demo.controller;

import com.parcial_saber_pro.demo.model.Estudiante;
import com.parcial_saber_pro.demo.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estudiantes")
@CrossOrigin(origins = "*")
public class EstudianteController {
    
    @Autowired
    private EstudianteService estudianteService;
    
    @GetMapping
    public List<Estudiante> getAllEstudiantes() {
        return estudianteService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> getEstudianteById(@PathVariable Long id) {
        Optional<Estudiante> estudiante = estudianteService.findById(id);
        return estudiante.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/registro/{numeroRegistro}")
    public ResponseEntity<Estudiante> getEstudianteByNumeroRegistro(@PathVariable String numeroRegistro) {
        Optional<Estudiante> estudiante = estudianteService.findByNumeroRegistro(numeroRegistro);
        return estudiante.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Estudiante createEstudiante(@RequestBody Estudiante estudiante) {
        return estudianteService.save(estudiante);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> updateEstudiante(@PathVariable Long id, @RequestBody Estudiante estudianteDetails) {
        Optional<Estudiante> estudiante = estudianteService.findById(id);
        if (estudiante.isPresent()) {
            Estudiante existingEstudiante = estudiante.get();
            // Actualizar campos
            if (estudianteDetails.getPrimerNombre() != null) {
                existingEstudiante.setPrimerNombre(estudianteDetails.getPrimerNombre());
            }
            if (estudianteDetails.getPrimerApellido() != null) {
                existingEstudiante.setPrimerApellido(estudianteDetails.getPrimerApellido());
            }
            if (estudianteDetails.getCorreoElectronico() != null) {
                existingEstudiante.setCorreoElectronico(estudianteDetails.getCorreoElectronico());
            }
            if (estudianteDetails.getNumeroTelefonico() != null) {
                existingEstudiante.setNumeroTelefonico(estudianteDetails.getNumeroTelefonico());
            }
            return ResponseEntity.ok(estudianteService.save(existingEstudiante));
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstudiante(@PathVariable Long id) {
        if (estudianteService.findById(id).isPresent()) {
            estudianteService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}