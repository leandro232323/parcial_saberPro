package com.parcial_saber_pro.demo.service;

import com.parcial_saber_pro.demo.model.ResultadoSaberPro;
import com.parcial_saber_pro.demo.repository.ResultadoSaberProRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ResultadoSaberProService {

    @Autowired
    private ResultadoSaberProRepository resultadoRepository;

    public List<ResultadoSaberPro> findAll() {
        List<ResultadoSaberPro> resultados = resultadoRepository.findAll();

        // DEBUG: Ver qué está cargando
        System.out.println("=== DEBUG RESULTADO SERVICE ===");
        System.out.println("Total resultados en BD: " + resultados.size());
        for (ResultadoSaberPro r : resultados) {
            System.out.println("ID: " + r.getId() +
                    " | Estudiante: "
                    + (r.getEstudiante() != null
                            ? r.getEstudiante().getPrimerNombre() + " " + r.getEstudiante().getPrimerApellido()
                            : "NULL")
                    +
                    " | Puntaje: " + r.getPuntajeGlobal());
        }
        System.out.println("=== FIN DEBUG ===");

        return resultados;
    }

    public Optional<ResultadoSaberPro> findById(Long id) {
        return resultadoRepository.findById(id);
    }

    public List<ResultadoSaberPro> findByEstudianteId(Long estudianteId) {
        return resultadoRepository.findByEstudianteId(estudianteId);
    }

    public Optional<ResultadoSaberPro> findByEstudianteNumeroRegistro(String numeroRegistro) {
        return resultadoRepository.findByEstudianteNumeroRegistro(numeroRegistro);
    }

    public ResultadoSaberPro save(ResultadoSaberPro resultado) {
        return resultadoRepository.save(resultado);
    }

    public void deleteById(Long id) {
        resultadoRepository.deleteById(id);
    }

    public List<ResultadoSaberPro> obtenerResultadosPorEstudiante(Long estudianteId) {
        return resultadoRepository.findByEstudianteId(estudianteId);
    }

    public List<ResultadoSaberPro> findEstudiantesDestacados() {
        // Buscar estudiantes con puntaje >= 180
        List<ResultadoSaberPro> todos = resultadoRepository.findAll();
        return todos.stream()
                .filter(r -> r.getPuntajeGlobal() != null && r.getPuntajeGlobal() >= 180)
                .toList();
    }
}