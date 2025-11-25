package com.parcial_saber_pro.demo.service;

import com.parcial_saber_pro.demo.model.Estudiante;
import com.parcial_saber_pro.demo.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EstudianteService {
    
    @Autowired
    private EstudianteRepository estudianteRepository;
    
    public List<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }
    
    public Optional<Estudiante> findById(Long id) {
        return estudianteRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Estudiante findByIdWithResultados(Long id) {
        Optional<Estudiante> estudianteOpt = estudianteRepository.findById(id);
        if (estudianteOpt.isPresent()) {
            Estudiante estudiante = estudianteOpt.get();
            // Forzar carga de resultados
            if (estudiante.getResultados() != null) {
                estudiante.getResultados().size();
            }
            return estudiante;
        }
        return null;
    }
    
    public Optional<Estudiante> findByNumeroRegistro(String numeroRegistro) {
        return estudianteRepository.findByNumeroRegistro(numeroRegistro);
    }
    
    public Optional<Estudiante> findByCorreoElectronico(String correoElectronico) {
        return estudianteRepository.findByCorreoElectronico(correoElectronico);
    }
    
    public Estudiante save(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }
    
    public void deleteById(Long id) {
        estudianteRepository.deleteById(id);
    }
}