package com.dr_api.dr_api.service.impl;

import com.dr_api.dr_api.model.Appointment;
import com.dr_api.dr_api.repository.AppointmentRepository;
import com.dr_api.dr_api.service.AppointmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repo;

    public AppointmentServiceImpl(AppointmentRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Appointment> findAll() {
        return repo.findAll();
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    @Transactional
    public Appointment save(Appointment appointment) {
        return repo.save(appointment);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
    
    @Override
    @Transactional
    public Appointment updateStatus(Long id, String newStatus) {
        // Implementación básica de actualización de estado
        return repo.findById(id).map(appointment -> {
            appointment.setStatus(newStatus);
            return repo.save(appointment);
        }).orElseThrow(() -> new RuntimeException("Cita no encontrada con ID: " + id));
    }
}