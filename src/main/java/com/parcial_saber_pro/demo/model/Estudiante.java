package com.parcial_saber_pro.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "estudiantes")
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "tipo_documento", nullable = false)
    private String tipoDocumento;
    
    @Column(name = "numero_documento", unique = true)
    private String numeroDocumento;
    
    @Column(name = "primer_apellido", nullable = false)
    private String primerApellido;
    
    @Column(name = "segundo_apellido")
    private String segundoApellido;
    
    @Column(name = "primer_nombre", nullable = false)
    private String primerNombre;
    
    @Column(name = "segundo_nombre")
    private String segundoNombre;
    
    @Column(name = "correo_electronico")
    private String correoElectronico;
    
    @Column(name = "numero_telefonico")
    private String numeroTelefonico;
    
    @Column(name = "numero_registro", unique = true, nullable = false)
    private String numeroRegistro;
    
    @Column(name = "password")
    private String password;
    
    // CAMPO NUEVO AGREGADO PARA SOLUCIONAR EL ERROR
    @Column(name = "nivel_ingles")
    private String nivelIngles;
    
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ResultadoSaberPro> resultados;
    
    // Constructores
    public Estudiante() {}
    
    public Estudiante(String tipoDocumento, String primerApellido, String primerNombre, String numeroRegistro) {
        this.tipoDocumento = tipoDocumento;
        this.primerApellido = primerApellido;
        this.primerNombre = primerNombre;
        this.numeroRegistro = numeroRegistro;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    
    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    
    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }
    
    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }
    
    public String getPrimerNombre() { return primerNombre; }
    public void setPrimerNombre(String primerNombre) { this.primerNombre = primerNombre; }
    
    public String getSegundoNombre() { return segundoNombre; }
    public void setSegundoNombre(String segundoNombre) { this.segundoNombre = segundoNombre; }
    
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }
    
    public String getNumeroTelefonico() { return numeroTelefonico; }
    public void setNumeroTelefonico(String numeroTelefonico) { this.numeroTelefonico = numeroTelefonico; }
    
    public String getNumeroRegistro() { return numeroRegistro; }
    public void setNumeroRegistro(String numeroRegistro) { this.numeroRegistro = numeroRegistro; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    // GETTER Y SETTER NUEVO PARA nivelIngles
    public String getNivelIngles() { 
        return nivelIngles != null ? nivelIngles : "No registrado"; 
    }
    public void setNivelIngles(String nivelIngles) { this.nivelIngles = nivelIngles; }
    
    public List<ResultadoSaberPro> getResultados() { return resultados; }
    public void setResultados(List<ResultadoSaberPro> resultados) { this.resultados = resultados; }
    
    // MÉTODOS HELPER
    public String getNombreCompleto() {
        return primerNombre + " " + (segundoNombre != null ? segundoNombre + " " : "") + 
               primerApellido + " " + (segundoApellido != null ? segundoApellido : "");
    }
}