package com.dr_api.dr_api.repository;

import com.dr_api.dr_api.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    // Este repositorio nos permite guardar y buscar clínicas
}