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
@CrossOrigin(origins = "http://localhost:5173") // Origen explícito para CORS
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // 1. GET: Listar todas las citas (Para llenar la tabla)
    @GetMapping
    public List<Appointment> getAll() {
        return appointmentService.findAll();
    }

    // 2. POST: Crear nueva cita
    @PostMapping
    public ResponseEntity<Appointment> create(@Valid @RequestBody Appointment appointment) {
        Appointment newAppointment = appointmentService.save(appointment);
        return new ResponseEntity<>(newAppointment, HttpStatus.CREATED);
    }

    // 3. DELETE: Eliminar cita por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (appointmentService.findById(id).isPresent()) {
            appointmentService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // 4. PATCH: Actualizar solo el estado de la cita
    // Se asume que el body es simple: { "status": "Finalizada" }
    @PatchMapping("/{id}/status")
    public ResponseEntity<Appointment> updateStatus(@PathVariable Long id, @RequestBody Appointment statusUpdate) {
        try {
            Appointment updatedAppointment = appointmentService.updateStatus(id, statusUpdate.getStatus());
            return ResponseEntity.ok(updatedAppointment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // =========================================================================
    // === ENDPOINTS PARA EL DASHBOARD PRO (IMPLEMENTACIÓN DE DATOS SIMULADOS) ===
    // =========================================================================

    // DTO para el gráfico de barras: { "label": "Ene", "value": 15 }
    public record MonthlyAppointmentSummary(String label, long value) {}

    /**
     * Endpoint 5: Resuelve la conexión del Gráfico (API_CHART_URL)
     * Ruta: /api/appointments/dashboard/citas-mensuales
     */
    @GetMapping("/dashboard/citas-mensuales") 
    public List<MonthlyAppointmentSummary> getFinishedAppointmentsSummary() {
        // Retorna datos simulados para que el componente BarChart.jsx funcione
        return List.of(
            new MonthlyAppointmentSummary("Jul", 25),
            new MonthlyAppointmentSummary("Ago", 32),
            new MonthlyAppointmentSummary("Sep", 45),
            new MonthlyAppointmentSummary("Oct", 38),
            new MonthlyAppointmentSummary("Nov", 50),
            new MonthlyAppointmentSummary("Dic", 65)
        );
    }
    
    // DTO para el resumen de agenda: { "medic", "time", "patient" }
    public record AgendaSummaryItem(String medic, String time, String patient) {}
    
    /**
     * Endpoint 6: Resuelve la conexión del Resumen de Agenda (API_AGENDA_SUMMARY_URL)
     * Ruta: /api/appointments/dashboard/resumen-agenda
     */
    @GetMapping("/dashboard/resumen-agenda")
    public List<AgendaSummaryItem> getAgendaSummary() {
        // Retorna datos simulados para que el resumen de agenda funcione
        return List.of(
            new AgendaSummaryItem("Dr. Sánchez", "09:00", "Juan Pérez"),
            new AgendaSummaryItem("Dr. González", "11:30", "María López"),
            new AgendaSummaryItem("Dr. Fachero", "15:00", "Carlos Ruiz"),
            new AgendaSummaryItem("Dr. Sánchez", "16:45", "Luisa Martínez")
        );
    }
}