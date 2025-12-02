package com.dr_api.dr_api.service;

import com.dr_api.dr_api.model.Admin;
import com.dr_api.dr_api.repository.AdminRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio para gestionar la lógica de negocio de los Administradores.
 * Incluye lógica de inicialización y encriptación de contraseña.
 */
@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Crea un administrador por defecto si no existe NINGUNO al inicio de la app.
     */
    @PostConstruct
    public void initializeAdmin() {
        // En un entorno de producción, esto debería ser solo para desarrollo.
        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            // Contraseña hasheada
            admin.setPassword(passwordEncoder.encode("password123")); 
            admin.setRole("ROLE_ADMIN"); 
            adminRepository.save(admin);
            System.out.println(">>> [INFO] Administrador inicial creado: 'admin' / 'password123' (ROLE_ADMIN)");
        }
    }
    
    public Admin save(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        if (admin.getRole() == null) {
            admin.setRole("ROLE_ADMIN");
        }
        return adminRepository.save(admin);
    }
}