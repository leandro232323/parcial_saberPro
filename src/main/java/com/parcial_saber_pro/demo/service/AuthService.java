package com.parcial_saber_pro.demo.service;

import com.parcial_saber_pro.demo.model.Estudiante;
import com.parcial_saber_pro.demo.model.Usuario;
import com.parcial_saber_pro.demo.model.RolUsuario;
import com.parcial_saber_pro.demo.repository.EstudianteRepository;
import com.parcial_saber_pro.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final EstudianteRepository estudianteRepository;

    public AuthService(UsuarioRepository usuarioRepository, EstudianteRepository estudianteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.estudianteRepository = estudianteRepository;
    }

    public boolean autenticar(String identificador, String password) {
        // Buscar primero en usuarios
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(identificador);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            return usuario.getPassword().equals(password);
        }

        // Si no encuentra, buscar en estudiantes por correo
        Optional<Estudiante> estudianteOpt = estudianteRepository.findByCorreoElectronico(identificador);
        if (estudianteOpt.isPresent()) {
            Estudiante estudiante = estudianteOpt.get();
            return estudiante.getPassword() != null && estudiante.getPassword().equals(password);
        }

        // Si no encuentra, buscar en estudiantes por número de registro
        Optional<Estudiante> estudianteRegistroOpt = estudianteRepository.findByNumeroRegistro(identificador);
        if (estudianteRegistroOpt.isPresent()) {
            Estudiante estudiante = estudianteRegistroOpt.get();
            return estudiante.getPassword() != null && estudiante.getPassword().equals(password);
        }

        return false;
    }

    public Optional<?> obtenerUsuario(String identificador) {
        // Buscar primero en usuarios
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(identificador);
        if (usuarioOpt.isPresent()) {
            return usuarioOpt;
        }

        // Buscar en estudiantes por correo
        Optional<Estudiante> estudianteCorreoOpt = estudianteRepository.findByCorreoElectronico(identificador);
        if (estudianteCorreoOpt.isPresent()) {
            return estudianteCorreoOpt;
        }

        // Buscar en estudiantes por número de registro
        Optional<Estudiante> estudianteRegistroOpt = estudianteRepository.findByNumeroRegistro(identificador);
        if (estudianteRegistroOpt.isPresent()) {
            return estudianteRegistroOpt;
        }

        return Optional.empty();
    }

    public void crearUsuarioCoordinador() {
        // Verificar si ya existe el usuario coordinador
        if (usuarioRepository.findByUsername("coordinador").isEmpty()) {
            Usuario coordinador = new Usuario();
            coordinador.setUsername("coordinador");
            coordinador.setPassword("admin123");
            coordinador.setRol(RolUsuario.COORDINADOR);
            usuarioRepository.save(coordinador);
            System.out.println("Usuario coordinador creado");
        }
    }
}