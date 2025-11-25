package com.parcial_saber_pro.demo.repository;

import com.parcial_saber_pro.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByUsername(String username);
    
    // MÉTODO CORREGIDO - buscar por la relación estudiante.id
    @Query("SELECT u FROM Usuario u WHERE u.estudiante.id = :estudianteId")
    Optional<Usuario> findByEstudiante_Id(@Param("estudianteId") Long estudianteId);
}