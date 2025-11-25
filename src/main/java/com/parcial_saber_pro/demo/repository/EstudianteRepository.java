package com.parcial_saber_pro.demo.repository;

import com.parcial_saber_pro.demo.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    Optional<Estudiante> findByNumeroRegistro(String numeroRegistro);
    Optional<Estudiante> findByNumeroDocumento(String numeroDocumento);
    Optional<Estudiante> findByCorreoElectronico(String correoElectronico);
    
    @Query("SELECT e FROM Estudiante e LEFT JOIN FETCH e.resultados WHERE e.id = :id")
    Optional<Estudiante> findByIdWithResultados(@Param("id") Long id);
}