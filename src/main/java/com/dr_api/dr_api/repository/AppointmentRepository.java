package com.dr_api.dr_api.repository;

import com.dr_api.dr_api.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // CRUD básico de Citas (Appointments)
}