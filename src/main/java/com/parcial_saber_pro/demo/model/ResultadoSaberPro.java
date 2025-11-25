package com.parcial_saber_pro.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resultados_saber_pro")
public class ResultadoSaberPro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // CAMBIA ESTA LÍNEA - AÑADE fetch = FetchType.EAGER
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;
    
    @Column(name = "puntaje_global")
    private Integer puntajeGlobal;
    
    @Column(name = "nivel_global")
    private String nivelGlobal;
    
    @Column(name = "comunicacion_escrita")
    private Integer comunicacionEscrita;
    
    @Column(name = "nivel_comunicacion_escrita")
    private String nivelComunicacionEscrita;
    
    @Column(name = "razonamiento_cuantitativo")
    private Integer razonamientoCuantitativo;
    
    @Column(name = "nivel_razonamiento_cuantitativo")
    private String nivelRazonamientoCuantitativo;
    
    @Column(name = "lectura_critica")
    private Integer lecturaCritica;
    
    @Column(name = "nivel_lectura_critica")
    private String nivelLecturaCritica;
    
    @Column(name = "competencias_ciudadanas")
    private Integer competenciasCiudadanas;
    
    @Column(name = "nivel_competencias_ciudadanas")
    private String nivelCompetenciasCiudadanas;
    
    @Column(name = "ingles")
    private Integer ingles;
    
    @Column(name = "nivel_ingles")
    private String nivelIngles;
    
    @Column(name = "nivel_ingles_cefr")
    private String nivelInglesCefr;
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    
    // Constructores
    public ResultadoSaberPro() {
        this.fechaRegistro = LocalDateTime.now();
    }
    
    public ResultadoSaberPro(Estudiante estudiante, Integer puntajeGlobal) {
        this.estudiante = estudiante;
        this.puntajeGlobal = puntajeGlobal;
        this.fechaRegistro = LocalDateTime.now();
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }
    
    public Integer getPuntajeGlobal() { return puntajeGlobal; }
    public void setPuntajeGlobal(Integer puntajeGlobal) { this.puntajeGlobal = puntajeGlobal; }
    
    public String getNivelGlobal() { return nivelGlobal; }
    public void setNivelGlobal(String nivelGlobal) { this.nivelGlobal = nivelGlobal; }
    
    public Integer getComunicacionEscrita() { return comunicacionEscrita; }
    public void setComunicacionEscrita(Integer comunicacionEscrita) { this.comunicacionEscrita = comunicacionEscrita; }
    
    public String getNivelComunicacionEscrita() { return nivelComunicacionEscrita; }
    public void setNivelComunicacionEscrita(String nivelComunicacionEscrita) { this.nivelComunicacionEscrita = nivelComunicacionEscrita; }
    
    public Integer getRazonamientoCuantitativo() { return razonamientoCuantitativo; }
    public void setRazonamientoCuantitativo(Integer razonamientoCuantitativo) { this.razonamientoCuantitativo = razonamientoCuantitativo; }
    
    public String getNivelRazonamientoCuantitativo() { return nivelRazonamientoCuantitativo; }
    public void setNivelRazonamientoCuantitativo(String nivelRazonamientoCuantitativo) { this.nivelRazonamientoCuantitativo = nivelRazonamientoCuantitativo; }
    
    public Integer getLecturaCritica() { return lecturaCritica; }
    public void setLecturaCritica(Integer lecturaCritica) { this.lecturaCritica = lecturaCritica; }
    
    public String getNivelLecturaCritica() { return nivelLecturaCritica; }
    public void setNivelLecturaCritica(String nivelLecturaCritica) { this.nivelLecturaCritica = nivelLecturaCritica; }
    
    public Integer getCompetenciasCiudadanas() { return competenciasCiudadanas; }
    public void setCompetenciasCiudadanas(Integer competenciasCiudadanas) { this.competenciasCiudadanas = competenciasCiudadanas; }
    
    public String getNivelCompetenciasCiudadanas() { return nivelCompetenciasCiudadanas; }
    public void setNivelCompetenciasCiudadanas(String nivelCompetenciasCiudadanas) { this.nivelCompetenciasCiudadanas = nivelCompetenciasCiudadanas; }
    
    public Integer getIngles() { return ingles; }
    public void setIngles(Integer ingles) { this.ingles = ingles; }
    
    public String getNivelIngles() { return nivelIngles; }
    public void setNivelIngles(String nivelIngles) { this.nivelIngles = nivelIngles; }
    
    public String getNivelInglesCefr() { return nivelInglesCefr; }
    public void setNivelInglesCefr(String nivelInglesCefr) { this.nivelInglesCefr = nivelInglesCefr; }
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}