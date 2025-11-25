package com.parcial_saber_pro.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolUsuario rol;
    
    @OneToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante; // Solo para usuarios con rol ESTUDIANTE
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    
    // Constructores
    public Usuario() {
        this.fechaRegistro = LocalDateTime.now();
    }
    
    public Usuario(String username, String password, RolUsuario rol) {
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.fechaRegistro = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public RolUsuario getRol() { return rol; }
    public void setRol(RolUsuario rol) { this.rol = rol; }
    
    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    // ← MÉTODO NUEVO QUE FALTABA - AGREGA ESTO
    public Long getEstudianteId() {
        if (estudiante != null) {
            return estudiante.getId();
        }
        return null;
    }
}