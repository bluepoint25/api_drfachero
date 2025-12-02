package com.dr_api.dr_api.service;

import com.dr_api.dr_api.model.Admin;
import com.dr_api.dr_api.repository.AdminRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Inicializa los usuarios por defecto al arrancar la aplicación.
     * Crea un Admin (Pro) y un Usuario (Estándar).
     */
    @PostConstruct
    public void initializeUsers() {
        // 1. Crear ADMIN (Acceso a Dashboard PRO)
        createUserIfNotExists("admin@drfachero.cl", "password123", "ROLE_ADMIN");

        // 2. Crear USUARIO ESTÁNDAR (Acceso a Dashboard Estándar)
        createUserIfNotExists("user@drfachero.cl", "password123", "ROLE_USER");
    }

    private void createUserIfNotExists(String username, String rawPassword, String role) {
        if (adminRepository.findByUsername(username).isEmpty()) {
            Admin user = new Admin();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setRole(role);
            adminRepository.save(user);
            logger.info(">>> [INIT] Usuario creado: {} / {} (Rol: {})", username, rawPassword, role);
        } else {
            logger.info(">>> [INIT] El usuario {} ya existe en la base de datos.", username);
        }
    }
    
    // Método para registro manual (opcional)
    public Admin save(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        if (admin.getRole() == null) {
            admin.setRole("ROLE_USER"); // Por defecto estándar si no se especifica
        }
        return adminRepository.save(admin);
    }
}