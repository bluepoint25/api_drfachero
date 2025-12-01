package com.dr_api.dr_api.service.impl;

import com.dr_api.dr_api.model.Patient;
import com.dr_api.dr_api.repository.PatientRepository;
import com.dr_api.dr_api.repository.SubscriptionRepository;
import com.dr_api.dr_api.service.PatientService;
import com.dr_api.dr_api.exception.PlanLimitExceededException; 
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional; // O usa org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional; // Necesario para findById

@Service
public class PatientServiceImpl implements PatientService {

    private static final int FREE_PLAN_LIMIT = 20;

    private final PatientRepository patientRepository;
    private final SubscriptionRepository subscriptionRepository;

    // CONSTRUCTOR ÚNICO: Inyecta ambas dependencias (PatientRepo y SubscriptionRepo)
    public PatientServiceImpl(PatientRepository patientRepository, 
                              SubscriptionRepository subscriptionRepository) {
        this.patientRepository = patientRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    @Transactional
    public Patient save(Patient patient) {
        // Asumimos que patient.getClinicaId() devuelve el ID de la clínica
        Long clinicaId = patient.getClinicaId();
        
        // 1. Obtener el tipo de plan (ej. 'PLAN_FREE', 'PLAN_PRO')
        String planType = subscriptionRepository.findPlanTypeByClinicaId(clinicaId);
        
        if (planType == null) {
            // Manejar caso donde no hay suscripción activa
            throw new RuntimeException("Clínica sin suscripción activa.");
        }

        // 2. Verificar el límite si el plan es FREE
        if ("PLAN_FREE".equalsIgnoreCase(planType)) {
            
            // 3. Contar los pacientes existentes para esta clínica
            long currentPatientCount = patientRepository.countByClinica_Id(clinicaId);
            
            // 4. Aplicar la restricción
            if (currentPatientCount >= FREE_PLAN_LIMIT) {
                throw new PlanLimitExceededException(
                    "Límite de pacientes excedido. El Plan Free permite un máximo de " + 
                    FREE_PLAN_LIMIT + " pacientes. Mejore a Plan Pro."
                );
            }
        }
        
        // Si el plan es PRO o el límite FREE no se ha alcanzado, se guarda el paciente.
        return patientRepository.save(patient);
    }
    
    // === Métodos CRUD RESTANTES (Usando patientRepository) ===

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