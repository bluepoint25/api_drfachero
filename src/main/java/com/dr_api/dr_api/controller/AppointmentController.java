package com.dr_api.dr_api.controller;

import com.dr_api.dr_api.model.Appointment;
import com.dr_api.dr_api.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "http://localhost:5173")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // 1. GET
    @GetMapping
    public List<Appointment> getAll() {
        return appointmentService.findAll();
    }

    // 2. POST
    @PostMapping
    public ResponseEntity<Appointment> create(@Valid @RequestBody Appointment appointment) {
        Appointment newAppointment = appointmentService.save(appointment);
        return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
    }
    
    // 3. PUT: Actualizar Cita Completa (NUEVO)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Appointment appointment) {
        if (appointmentService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        appointment.setId(id);
        Appointment updated = appointmentService.save(appointment);
        return ResponseEntity.ok(updated);
    }

    // 4. DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (appointmentService.findById(id).isPresent()) {
            appointmentService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // 5. PATCH: Actualizar solo estado
    @PatchMapping("/{id}/status")
    public ResponseEntity<Appointment> updateStatus(@PathVariable Long id, @RequestBody Appointment statusUpdate) {
        try {
            Appointment updatedAppointment = appointmentService.updateStatus(id, statusUpdate.getStatus());
            return ResponseEntity.ok(updatedAppointment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // --- DATOS SIMULADOS DASHBOARD ---
    public record MonthlyAppointmentSummary(String label, long value) {}
    @GetMapping("/dashboard/citas-mensuales") 
    public List<MonthlyAppointmentSummary> getFinishedAppointmentsSummary() {
        return List.of(
            new MonthlyAppointmentSummary("Jul", 25),
            new MonthlyAppointmentSummary("Ago", 32),
            new MonthlyAppointmentSummary("Sep", 45),
            new MonthlyAppointmentSummary("Oct", 38),
            new MonthlyAppointmentSummary("Nov", 50),
            new MonthlyAppointmentSummary("Dic", 65)
        );
    }
    
    public record AgendaSummaryItem(String medic, String time, String patient) {}
    @GetMapping("/dashboard/resumen-agenda")
    public List<AgendaSummaryItem> getAgendaSummary() {
        return List.of(
            new AgendaSummaryItem("Dr. Sánchez", "09:00", "Juan Pérez"),
            new AgendaSummaryItem("Dr. González", "11:30", "María López"),
            new AgendaSummaryItem("Dr. Fachero", "15:00", "Carlos Ruiz")
        );
    }
}