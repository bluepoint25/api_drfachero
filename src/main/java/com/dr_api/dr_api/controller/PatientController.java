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
// FIX: Cambiamos "*" por el origen exacto de desarrollo de React
@CrossOrigin(origins = "http://localhost:5173") 
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // 1. GET: Listar todos los pacientes
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<Patient> patients = patientService.findAll();
            return ResponseEntity.ok(patients);
        } catch (Exception e) {
            e.printStackTrace(); // Esto imprimirá el error en la consola de Spring Boot
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al obtener pacientes: " + e.getMessage());
        }
    }

    // 2. GET: Obtener paciente por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Optional<Patient> patient = patientService.findById(id);
            return patient.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al obtener paciente: " + e.getMessage());
        }
    }

    // 3. POST: Crear nuevo paciente
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Patient patient) {
        try {
            Patient newPatient = patientService.save(patient);
            return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
            
        } catch (PlanLimitExceededException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body(e.getMessage());
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al guardar el paciente: " + e.getMessage());
        }
    }

    // 4. DELETE: Eliminar paciente por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            patientService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}