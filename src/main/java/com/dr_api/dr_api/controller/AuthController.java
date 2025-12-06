package com.dr_api.dr_api.controller;

import com.dr_api.dr_api.dto.AuthRequest;
import com.dr_api.dr_api.dto.AuthResponse;
import com.dr_api.dr_api.model.Admin;
import com.dr_api.dr_api.security.AdminDetailsService;
import com.dr_api.dr_api.security.JwtUtil;
import com.dr_api.dr_api.service.AdminService; 
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") 
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AdminDetailsService adminDetailsService;
    private final JwtUtil jwtUtil;
    private final AdminService adminService; 

    public AuthController(AuthenticationManager authenticationManager, 
                          AdminDetailsService adminDetailsService, 
                          JwtUtil jwtUtil,
                          AdminService adminService) {
        this.authenticationManager = authenticationManager;
        this.adminDetailsService = adminDetailsService;
        this.jwtUtil = jwtUtil;
        this.adminService = adminService;
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@Valid @RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

        
            if (authentication.isAuthenticated()) {
                final UserDetails userDetails = adminDetailsService.loadUserByUsername(authRequest.getUsername());           
                final String jwt = jwtUtil.generateToken(userDetails);
                String role = userDetails.getAuthorities().stream().findFirst().get().getAuthority();

                return ResponseEntity.ok(new AuthResponse(jwt, userDetails.getUsername(), role));
            } else {
               
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error de autenticación: " + e.getMessage());
        }
    }
    @PostMapping("/register")
    public ResponseEntity<Admin> registerAdmin(@Valid @RequestBody Admin admin) {
        Admin newAdmin = adminService.save(admin);
        return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
    }
}