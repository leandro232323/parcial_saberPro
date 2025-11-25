package com.parcial_saber_pro.demo.controller;

import com.parcial_saber_pro.demo.model.Estudiante;
import com.parcial_saber_pro.demo.model.ResultadoSaberPro;
import com.parcial_saber_pro.demo.service.EstudianteService;
import com.parcial_saber_pro.demo.service.ResultadoSaberProService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping("/")
public class ViewController {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private ResultadoSaberProService resultadoService;

    @GetMapping
    public String dashboard(Model model) {
        List<Estudiante> estudiantes = estudianteService.findAll();
        List<ResultadoSaberPro> resultados = resultadoService.findAll();
        List<ResultadoSaberPro> destacados = resultadoService.findEstudiantesDestacados();

        // Calcular promedio de forma segura
        double promedio = 0.0;
        if (!resultados.isEmpty()) {
            int suma = 0;
            int count = 0;
            for (ResultadoSaberPro resultado : resultados) {
                if (resultado.getPuntajeGlobal() != null) {
                    suma += resultado.getPuntajeGlobal();
                    count++;
                }
            }
            promedio = count > 0 ? (double) suma / count : 0.0;
        }

        model.addAttribute("pageTitle", "Dashboard");
        model.addAttribute("totalEstudiantes", estudiantes.size());
        model.addAttribute("totalResultados", resultados.size());
        model.addAttribute("estudiantesDestacados", destacados.size());
        model.addAttribute("promedioGlobal", String.format("%.1f", promedio));

        // Últimos 5 resultados
        List<ResultadoSaberPro> ultimosResultados = resultados.size() > 5 ? resultados.subList(0, 5) : resultados;
        model.addAttribute("ultimosResultados", ultimosResultados);

        return "index";
    }

    @GetMapping("/estudiantes")
    public String listarEstudiantes(Model model) {
        try {
            System.out.println("=== INICIANDO LISTA ESTUDIANTES ===");

            List<Estudiante> estudiantes = estudianteService.findAll();
            System.out.println("Estudiantes encontrados: " + estudiantes.size());

            // DEBUG - Verificar cada estudiante
            for (Estudiante e : estudiantes) {
                System.out.println("ID: " + e.getId() +
                        " | Nombre: " + e.getPrimerNombre() + " " + e.getPrimerApellido() +
                        " | Doc: " + e.getNumeroDocumento());
            }

            model.addAttribute("pageTitle", "Gestión de Estudiantes");
            model.addAttribute("estudiantes", estudiantes);

            System.out.println("=== LISTA COMPLETADA ===");
            return "estudiantes/lista";

        } catch (Exception e) {
            System.err.println("ERROR en listarEstudiantes: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error al cargar estudiantes: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/estudiantes/nuevo")
    public String nuevoEstudiante(Model model) {
        model.addAttribute("pageTitle", "Nuevo Estudiante");
        model.addAttribute("estudiante", new Estudiante());
        return "estudiantes/form";
    }

    @GetMapping("/estudiantes/{id}")
    public String verEstudiante(@PathVariable Long id, Model model) {
        try {
            System.out.println("=== CARGANDO DETALLE ESTUDIANTE ID: " + id + " ===");

            // LÍNEA CORREGIDA - Sin .orElseThrow()
            Estudiante estudiante = estudianteService.findByIdWithResultados(id);
            
            if (estudiante == null) {
                throw new RuntimeException("Estudiante no encontrado con ID: " + id);
            }

            // DEBUG - Verificar datos
            System.out.println("Estudiante: " + estudiante.getPrimerNombre() + " " + estudiante.getPrimerApellido());
            System.out.println(
                    "Resultados: " + (estudiante.getResultados() != null ? estudiante.getResultados().size() : 0));

            model.addAttribute("pageTitle", "Detalle Estudiante");
            model.addAttribute("estudiante", estudiante);

            System.out.println("=== DETALLE CARGADO EXITOSAMENTE ===");
            return "estudiantes/detalle";

        } catch (Exception e) {
            System.err.println("ERROR en verEstudiante: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/estudiantes?error=not_found";
        }
    }

    @PostMapping("/estudiantes/guardar")
    public String guardarEstudiante(@ModelAttribute Estudiante estudiante) {
        // Si no se ingresa contraseña, usar el número de documento como contraseña por
        // defecto
        if (estudiante.getPassword() == null || estudiante.getPassword().trim().isEmpty()) {
            estudiante.setPassword(estudiante.getNumeroDocumento());
        }

        estudianteService.save(estudiante);
        return "redirect:/estudiantes";
    }

    @GetMapping("/estudiantes/editar/{id}")
    public String editarEstudiante(@PathVariable Long id, Model model) {
        // LÍNEA CORREGIDA - Con Optional correcto
        Optional<Estudiante> estudianteOpt = estudianteService.findById(id);
        if (estudianteOpt.isEmpty()) {
            throw new RuntimeException("Estudiante no encontrado");
        }
        
        Estudiante estudiante = estudianteOpt.get();
        model.addAttribute("pageTitle", "Editar Estudiante");
        model.addAttribute("estudiante", estudiante);
        return "estudiantes/form";
    }

    @GetMapping("/estudiantes/eliminar/{id}")
    public String eliminarEstudiante(@PathVariable Long id) {
        estudianteService.deleteById(id);
        return "redirect:/estudiantes";
    }

    @GetMapping("/resultados")
    public String listarResultados(Model model) {
        try {
            System.out.println("=== 🔍 INICIANDO DEBUG DETALLADO DE RESULTADOS ===");

            List<ResultadoSaberPro> resultados = resultadoService.findAll();

            System.out.println("📊 TOTAL resultados del SERVICE: " + resultados.size());

            // DEBUG SUPER DETALLADO
            System.out.println("=== 📝 DETALLE DE CADA RESULTADO ===");
            for (int i = 0; i < resultados.size(); i++) {
                ResultadoSaberPro r = resultados.get(i);
                System.out.println("[" + (i + 1) + "] ID: " + r.getId());
                System.out.println("   Puntaje Global: " + r.getPuntajeGlobal());
                System.out.println("   Nivel Global: " + r.getNivelGlobal());

                if (r.getEstudiante() != null) {
                    System.out.println("   Estudiante ID: " + r.getEstudiante().getId());
                    System.out.println("   Nombre: " + r.getEstudiante().getPrimerNombre() + " "
                            + r.getEstudiante().getPrimerApellido());
                    System.out.println("   Registro: " + r.getEstudiante().getNumeroRegistro());
                } else {
                    System.out.println("   ⚠️⚠️⚠️ ESTUDIANTE ES NULL - ESTE ES EL PROBLEMA ⚠️⚠️⚠️");
                }
                System.out.println("   ---");
            }

            // AGREGAR ESTO: Verificar si hay duplicados en el ID
            Set<Long> ids = new HashSet<>();
            for (ResultadoSaberPro r : resultados) {
                if (!ids.add(r.getId())) {
                    System.out.println("🚨🚨🚨 DUPLICADO ENCONTRADO - ID: " + r.getId());
                }
            }

            model.addAttribute("pageTitle", "Resultados Saber Pro");
            model.addAttribute("resultados", resultados);

            System.out.println("=== ✅ DEBUG COMPLETADO - Enviando " + resultados.size() + " resultados a la vista ===");
            return "resultados/lista";

        } catch (Exception e) {
            System.err.println("❌ ERROR en listarResultados: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error al cargar resultados: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/resultados/nuevo")
    public String nuevoResultado(@RequestParam(value = "estudianteId", required = false) Long estudianteId,
            Model model) {
        List<Estudiante> estudiantes = estudianteService.findAll();

        System.out.println("=== DEBUG: Cargando formulario nuevo resultado ===");
        System.out.println("EstudianteId recibido: " + estudianteId);
        System.out.println("Total estudiantes en BD: " + estudiantes.size());

        ResultadoSaberPro resultado = new ResultadoSaberPro();

        // Si viene un estudianteId, establecerlo en el resultado
        if (estudianteId != null) {
            // LÍNEA CORREGIDA - Con Optional correcto
            Optional<Estudiante> estudianteOpt = estudianteService.findById(estudianteId);
            if (estudianteOpt.isPresent()) {
                Estudiante estudiante = estudianteOpt.get();
                resultado.setEstudiante(estudiante);
                System.out.println(
                        "Estudiante establecido: " + estudiante.getPrimerNombre() + " " + estudiante.getPrimerApellido());
            } else {
                throw new RuntimeException("Estudiante no encontrado con ID: " + estudianteId);
            }
        }

        model.addAttribute("pageTitle", "Registrar Resultado");
        model.addAttribute("resultado", resultado);
        model.addAttribute("estudiantes", estudiantes);

        return "resultados/form";
    }

    @GetMapping("/resultados/editar/{id}")
    public String editarResultado(@PathVariable Long id, Model model) {
        // LÍNEA CORREGIDA - Con Optional correcto
        Optional<ResultadoSaberPro> resultadoOpt = resultadoService.findById(id);
        if (resultadoOpt.isEmpty()) {
            throw new RuntimeException("Resultado no encontrado");
        }
        
        ResultadoSaberPro resultado = resultadoOpt.get();
        List<Estudiante> estudiantes = estudianteService.findAll();

        model.addAttribute("pageTitle", "Editar Resultado");
        model.addAttribute("resultado", resultado);
        model.addAttribute("estudiantes", estudiantes);
        return "resultados/form";
    }

    // MÉTODO ACTUALIZADO - Maneja tanto creación como edición
    @PostMapping("/resultados/guardar")
    public String guardarResultado(@ModelAttribute ResultadoSaberPro resultado) {
        // Calcular nivel global automáticamente
        calcularNivelGlobal(resultado);

        resultadoService.save(resultado);
        return "redirect:/resultados";
    }

    // MÉTODO NUEVO - Para edición específica con ID en la URL
    @PostMapping("/resultados/guardar/{id}")
    public String actualizarResultado(@PathVariable Long id, @ModelAttribute ResultadoSaberPro resultado) {
        resultado.setId(id);
        // Calcular nivel global automáticamente
        calcularNivelGlobal(resultado);

        resultadoService.save(resultado);
        return "redirect:/resultados";
    }

    @GetMapping("/resultados/eliminar/{id}")
    public String eliminarResultado(@PathVariable Long id) {
        resultadoService.deleteById(id);
        return "redirect:/resultados";
    }

    @GetMapping("/resultados/{id}")
    public String verResultado(@PathVariable Long id, Model model) {
        // LÍNEA CORREGIDA - Con Optional correcto
        Optional<ResultadoSaberPro> resultadoOpt = resultadoService.findById(id);
        if (resultadoOpt.isEmpty()) {
            throw new RuntimeException("Resultado no encontrado");
        }
        
        ResultadoSaberPro resultado = resultadoOpt.get();
        model.addAttribute("pageTitle", "Resultado: " + resultado.getEstudiante().getPrimerNombre());
        model.addAttribute("resultado", resultado);
        return "resultados/detalle";
    }

    @GetMapping("/informes")
    public String informes(Model model) {
        model.addAttribute("pageTitle", "Informes y Reportes");
        return "informes/lista";
    }

    @GetMapping("/beneficios")
    public String beneficios(Model model) {
        List<ResultadoSaberPro> destacados = resultadoService.findEstudiantesDestacados();
        model.addAttribute("pageTitle", "Beneficios por Puntaje");
        model.addAttribute("destacados", destacados);
        return "beneficios/lista";
    }

    // MÉTODO PRIVADO PARA CALCULAR NIVEL GLOBAL
    private void calcularNivelGlobal(ResultadoSaberPro resultado) {
        if (resultado.getPuntajeGlobal() != null) {
            Integer puntaje = resultado.getPuntajeGlobal();
            if (puntaje >= 241) {
                resultado.setNivelGlobal("Nivel 4 - Excelente");
            } else if (puntaje >= 211) {
                resultado.setNivelGlobal("Nivel 3 - Avanzado");
            } else if (puntaje >= 180) {
                resultado.setNivelGlobal("Nivel 2 - Intermedio");
            } else {
                resultado.setNivelGlobal("Nivel 1 - Básico");
            }
        }
    }
}