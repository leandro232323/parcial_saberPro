package com.parcial_saber_pro.demo.service;

import com.parcial_saber_pro.demo.model.Usuario;
import com.parcial_saber_pro.demo.model.Estudiante;
import com.parcial_saber_pro.demo.model.RolUsuario;
import com.parcial_saber_pro.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    public Usuario crearUsuarioEstudiante(Estudiante estudiante, String password) {
        String username = generarUsernameEstudiante(estudiante);
        
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setRol(RolUsuario.ESTUDIANTE);
        usuario.setEstudiante(estudiante);
        
        return usuarioRepository.save(usuario);
    }
    
    private String generarUsernameEstudiante(Estudiante estudiante) {
        // Ejemplo: cristian.ortiz
        return estudiante.getPrimerNombre().toLowerCase() + "." + 
               estudiante.getPrimerApellido().toLowerCase();
    }
}