package com.dr_api.dr_api.service;

import com.dr_api.dr_api.model.Appointment;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {
    List<Appointment> findAll();
    Optional<Appointment> findById(Long id);
    Appointment save(Appointment appointment);
    void deleteById(Long id);
    // Para la actualización de estado
    Appointment updateStatus(Long id, String newStatus);
}