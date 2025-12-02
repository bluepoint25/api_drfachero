package com.dr_api.dr_api.controller;

import com.dr_api.dr_api.dto.AuthRequest;
import com.dr_api.dr_api.dto.AuthResponse;
import com.dr_api.dr_api.model.Admin;
import com.dr_api.dr_api.security.AdminDetailsService;
import com.dr_api.dr_api.security.JwtUtil;
import com.dr_api.dr_api.service.AdminService; // Para el endpoint de registro (opcional)
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // Endpoint base: http://localhost:8080/api/auth
@CrossOrigin(origins = "http://localhost:5173") 
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AdminDetailsService adminDetailsService;
    private final JwtUtil jwtUtil;
    private final AdminService adminService; // Usado para el registro (opcional)

    public AuthController(AuthenticationManager authenticationManager, 
                          AdminDetailsService adminDetailsService, 
                          JwtUtil jwtUtil,
                          AdminService adminService) {
        this.authenticationManager = authenticationManager;
        this.adminDetailsService = adminDetailsService;
        this.jwtUtil = jwtUtil;
        this.adminService = adminService;
    }

    // ===============================================
    // 1. LOGIN (Generar JWT)
    // Ruta: POST /api/auth/login
    // ===============================================
    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@Valid @RequestBody AuthRequest authRequest) {
        try {
            // 1. Autenticar las credenciales usando AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            // 2. Si la autenticación es exitosa, obtener los detalles del usuario
            if (authentication.isAuthenticated()) {
                final UserDetails userDetails = adminDetailsService.loadUserByUsername(authRequest.getUsername());
                
                // 3. Generar el JWT
                final String jwt = jwtUtil.generateToken(userDetails);
                
                // 4. Crear la respuesta, extrayendo el rol
                String role = userDetails.getAuthorities().stream().findFirst().get().getAuthority();

                return ResponseEntity.ok(new AuthResponse(jwt, userDetails.getUsername(), role));
            } else {
                // Esto debería ser capturado por la excepción, pero por si acaso.
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
            }

        } catch (Exception e) {
            // Captura errores de autenticación (ej. BadCredentialsException)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error de autenticación: " + e.getMessage());
        }
    }
    
    // ===============================================
    // 2. REGISTRAR ADMIN (Solo para fines de prueba/setup inicial)
    // Ruta: POST /api/auth/register
    // NOTA: En un entorno real, esta ruta DEBE ser protegida o eliminada.
    // ===============================================
    @PostMapping("/register")
    public ResponseEntity<Admin> registerAdmin(@Valid @RequestBody Admin admin) {
        Admin newAdmin = adminService.save(admin);
        return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
    }
}