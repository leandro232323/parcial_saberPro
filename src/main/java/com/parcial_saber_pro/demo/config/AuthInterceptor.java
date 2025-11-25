package com.parcial_saber_pro.demo.config;

import com.parcial_saber_pro.demo.model.RolUsuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
            throws Exception {
        
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();
        
        // Rutas públicas
        if (uri.startsWith("/auth/login") || uri.equals("/auth/login")) {
            return true;
        }
        
        // Si no hay sesión, redirigir a login
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("/auth/login");
            return false;
        }
        
        // Verificar permisos por rol
        String rol = (String) session.getAttribute("rol");
        
        // Rutas solo para coordinador
        if (uri.startsWith("/estudiantes/nuevo") || 
            uri.startsWith("/estudiantes/editar") ||
            uri.startsWith("/estudiantes/eliminar") ||
            uri.startsWith("/resultados/nuevo") ||
            uri.startsWith("/resultados/editar") ||
            uri.startsWith("/resultados/eliminar")) {
            
            if (!RolUsuario.COORDINADOR.name().equals(rol)) {
                response.sendRedirect("/acceso-denegado");
                return false;
            }
        }
        
        return true;
    }
}