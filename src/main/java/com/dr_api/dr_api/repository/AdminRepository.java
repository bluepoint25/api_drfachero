package com.dr_api.dr_api.repository;

import com.dr_api.dr_api.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    
    // Método crucial para buscar al Admin por nombre de usuario durante el login
    Optional<Admin> findByUsername(String username);
}