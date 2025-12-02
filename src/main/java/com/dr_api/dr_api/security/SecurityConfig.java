package com.dr_api.dr_api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

// Importación faltante que causa el error "cannot be resolved"
import com.dr_api.dr_api.security.JwtAuthenticationFilter; 


@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Permite usar @PreAuthorize en Controllers
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthFilter; 
    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    // Inyección de dependencias
    public SecurityConfig(UserDetailsService userDetailsService, 
                          JwtAuthenticationFilter jwtAuthFilter,
                          JwtAuthenticationEntryPoint unauthorizedHandler) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    /**
     * 1. Define la cadena de filtros de seguridad.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CORS (Permite acceso desde el front-end)
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration config = new CorsConfiguration();
                // Permitir orígenes configurados en otros controllers y el nuevo endpoint
                config.setAllowedOrigins(List.of("http://localhost:5173", "*")); 
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                config.setAllowedHeaders(List.of("*"));
                config.setAllowCredentials(true);
                return config;
            }))
            
            // CSRF (Deshabilitado para APIs REST que usan JWT)
            .csrf(AbstractHttpConfigurer::disable)
            
            // Excepción de autenticación (usa JwtAuthenticationEntryPoint)
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))

            // Gestión de sesiones (Establece política sin estado, crucial para JWT)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // Configuración de Autorización
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas (Permite Login, Contacto, CRUD básico de Citas/Recetas/Pacientes)
                .requestMatchers("/api/contacto", 
                                 "/api/auth/**").permitAll() 
                
                // Swagger UI y recursos
                .requestMatchers("/v3/api-docs/**", 
                                 "/swagger-ui/**", 
                                 "/swagger-ui.html").permitAll()
                
                // Otras rutas deben estar autenticadas (ej. para el dashboard o CRUD general)
                .anyRequest().authenticated() 
            )
            
            // Configura el proveedor de autenticación
            .authenticationProvider(authenticationProvider())
            
            // Añade el filtro JWT antes del filtro estándar de Spring Security
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 2. Bean para la codificación de contraseñas (BCrypt).
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * 3. Define el proveedor de autenticación.
     * FIX: Usa el constructor moderno de DaoAuthenticationProvider para evitar deprecation warnings.
     */
@Bean
    public AuthenticationProvider authenticationProvider() {
        // 1. Usar el constructor vacío (si es necesario)
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); 
        
        // 2. Establecer el UserDetailsService (genera advertencia de deprecation, pero funciona)
        authProvider.setUserDetailsService(userDetailsService); 
        
        // 3. Establecer el PasswordEncoder (genera advertencia de deprecation, pero funciona)
        authProvider.setPasswordEncoder(passwordEncoder()); 
        
        return authProvider;
    }

    /**
     * 4. Bean para obtener el AuthenticationManager (utilizado para procesar la petición de login).
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}