package com.dr_api.dr_api.repository;

import com.dr_api.dr_api.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("SELECT COUNT(p) FROM Patient p WHERE p.clinica.id = :clinicaId")
    long countByClinica_Id(@Param("clinicaId") Long clinicaId);
}