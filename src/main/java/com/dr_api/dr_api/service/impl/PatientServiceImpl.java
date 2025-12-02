package com.dr_api.dr_api.service.impl;

import com.dr_api.dr_api.model.Clinic;
import com.dr_api.dr_api.model.Patient;
import com.dr_api.dr_api.repository.ClinicRepository; // IMPORTANTE: Agregamos esto
import com.dr_api.dr_api.repository.PatientRepository;
import com.dr_api.dr_api.repository.SubscriptionRepository;
import com.dr_api.dr_api.service.PatientService;
import com.dr_api.dr_api.exception.PlanLimitExceededException; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Corregido el import a Spring
import java.util.List;
import java.util.Optional; 

@Service
public class PatientServiceImpl implements PatientService {

    // Se mantiene la constante
    private static final int FREE_PLAN_LIMIT = 20;

    private final PatientRepository patientRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ClinicRepository clinicRepository; // Nueva inyección

    // Constructor actualizado con ClinicRepository
    public PatientServiceImpl(PatientRepository patientRepository, 
                              SubscriptionRepository subscriptionRepository,
                              ClinicRepository clinicRepository) {
        this.patientRepository = patientRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.clinicRepository = clinicRepository;
    }

    @Override
    @Transactional
    public Patient save(Patient patient) {
        
        // 1. OBTENCIÓN DINÁMICA DE LA CLÍNICA
        // En lugar de inventar una clínica con ID 1, buscamos la primera que exista en la BD.
        Clinic existingClinic = clinicRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Error Crítico: No hay ninguna clínica registrada en el sistema."));
        
        // 2. Asignamos la clínica real encontrada
        patient.setClinica(existingClinic); 
        
        // 3. (Lógica de planes ignorada temporalmente por seguridad, se puede descomentar después)
        
        // 4. Guardar paciente
        return patientRepository.save(patient);
    }
    
    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }
    
    @Override
    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        patientRepository.deleteById(id);
    }
}