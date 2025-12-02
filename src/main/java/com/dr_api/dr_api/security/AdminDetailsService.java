package com.dr_api.dr_api.security;

import com.dr_api.dr_api.model.Admin;
import com.dr_api.dr_api.repository.AdminRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementación de UserDetailsService para cargar la información del Admin
 * desde la base de datos (tabla ADMIN_USER).
 */
@Service
public class AdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    // Inyección del repositorio
    public AdminDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Carga el usuario por su nombre de usuario.
     * Es el método principal que usa Spring Security durante el proceso de autenticación.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca al Admin por su username en la base de datos
        Admin admin = adminRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Administrador no encontrado: " + username));
        
        // El objeto Admin ya implementa UserDetails, por lo que se devuelve directamente.
        return admin;
    }
}