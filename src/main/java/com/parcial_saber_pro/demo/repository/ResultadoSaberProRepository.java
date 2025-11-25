package com.parcial_saber_pro.demo.repository;

import com.parcial_saber_pro.demo.model.ResultadoSaberPro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ResultadoSaberProRepository extends JpaRepository<ResultadoSaberPro, Long> {
    List<ResultadoSaberPro> findByEstudianteId(Long estudianteId);
    Optional<ResultadoSaberPro> findByEstudianteNumeroRegistro(String numeroRegistro);
    List<ResultadoSaberPro> findByPuntajeGlobalGreaterThanEqual(Integer puntaje);
}