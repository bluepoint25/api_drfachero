package com.dr_api.dr_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro que se ejecuta una vez por cada petición para validar el JWT
 * y establecer la autenticación en el contexto de seguridad de Spring.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AdminDetailsService adminDetailsService;

    // Inyección de dependencias
    public JwtAuthenticationFilter(JwtUtil jwtUtil, AdminDetailsService adminDetailsService) {
        this.jwtUtil = jwtUtil;
        this.adminDetailsService = adminDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 1. Verificar si el header existe y comienza con "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extraer el token y el username
        jwt = authHeader.substring(7); // "Bearer ".length() == 7
        try {
            username = jwtUtil.extractUsername(jwt);
        } catch (Exception e) {
            // Maneja tokens malformados, expirados, etc. (se ignora aquí, el SecurityContextHolder vacío forzará el 401 más adelante)
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Si el username es válido y no hay autenticación activa en el contexto
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // Cargar los detalles del usuario
            UserDetails userDetails = this.adminDetailsService.loadUserByUsername(username);

            // 4. Validar el token
            if (jwtUtil.validateToken(jwt, userDetails)) {
                
                // Si es válido, crear un objeto de autenticación
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, 
                        null, // La contraseña ya no es necesaria, el token la reemplaza
                        userDetails.getAuthorities()
                );
                
                // Añadir detalles de la petición (IP, etc.)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                // 5. Establecer la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}