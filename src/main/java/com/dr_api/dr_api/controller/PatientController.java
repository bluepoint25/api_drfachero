package com.dr_api.dr_api.controller;

import com.dr_api.dr_api.model.Patient;
import com.dr_api.dr_api.service.PatientService;
import com.dr_api.dr_api.exception.PlanLimitExceededException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "http://localhost:5173") 
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // 1. GET: Listar
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<Patient> patients = patientService.findAll();
            return ResponseEntity.ok(patients);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al obtener pacientes: " + e.getMessage());
        }
    }

    // 2. GET: Por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Patient> patient = patientService.findById(id);
        return patient.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. POST: Crear
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Patient patient) {
        try {
            Patient newPatient = patientService.save(patient);
            return ResponseEntity.ok(newPatient);
        } catch (PlanLimitExceededException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al guardar: " + e.getMessage());
        }
    }

    // 4. PUT: Actualizar (NUEVO)
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Patient patient) {
        try {
            if (patientService.findById(id).isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            patient.setId(id); // Asegurar que actualizamos el ID correcto
            Patient updatedPatient = patientService.save(patient);
            return ResponseEntity.ok(updatedPatient);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al actualizar: " + e.getMessage());
        }
    }

    // 5. DELETE: Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            patientService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}