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
    // === NUEVOS ENDPOINTS PARA EL DASHBOARD PRO (Resuelven "Failed to fetch") ===
    // =========================================================================

    // DTO para el gráfico de barras: { "label": "Ene", "value": 15 }
    public record MonthlyAppointmentSummary(String label, long value) {}

    /**
     * Endpoint 5: Resuelve la conexión del Gráfico (API_CHART_URL)
     * Ruta: /api/appointments/dashboard/citas-mensuales
     */
    @GetMapping("/dashboard/citas-mensuales") 
    public List<MonthlyAppointmentSummary> getFinishedAppointmentsSummary() {
        // Datos mock para el BarChart
        return List.of(
            new MonthlyAppointmentSummary("Ene", 15),
            new MonthlyAppointmentSummary("Feb", 22),
            new MonthlyAppointmentSummary("Mar", 18),
            new MonthlyAppointmentSummary("Abr", 30),
            new MonthlyAppointmentSummary("May", 25)
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
        // Datos mock para el resumen de Agenda
        return List.of(
            new AgendaSummaryItem("Dra. Sánchez", "09:00", "Juan Pérez"),
            new AgendaSummaryItem("Dr. González", "11:30", "María López"),
            new AgendaSummaryItem("Dra. Sánchez", "15:00", "Carlos Ruiz")
        );
    }
}