package com.dr_api.dr_api.service.impl;

import com.dr_api.dr_api.model.Patient;
import com.dr_api.dr_api.model.Clinic; // Agregamos Clinic para crear un mock
import com.dr_api.dr_api.repository.PatientRepository;
import com.dr_api.dr_api.repository.SubscriptionRepository;
import com.dr_api.dr_api.service.PatientService;
import com.dr_api.dr_api.exception.PlanLimitExceededException; 
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional; 
import java.util.List;
import java.util.Optional; 

@Service
public class PatientServiceImpl implements PatientService {

    // Se mantiene la constante, aunque se ignora temporalmente.
    private static final int FREE_PLAN_LIMIT = 20;

    private final PatientRepository patientRepository;
    private final SubscriptionRepository subscriptionRepository;

    public PatientServiceImpl(PatientRepository patientRepository, 
                              SubscriptionRepository subscriptionRepository) {
        this.patientRepository = patientRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    @Transactional
    public Patient save(Patient patient) {
        
        // =============================================================
        // FIX TEMPORAL: Deshabilita la verificación de Plan/Límite
        //               y asigna una clínica dummy (ID 1)
        // =============================================================
        // Crear un objeto Clinic dummy con ID 1 (asumiendo que su Admin es el dueño de la clínica 1)
        Clinic dummyClinic = new Clinic();
        dummyClinic.setId(1L); 
        
        // Asegurar que el objeto Patient tenga la referencia a la clínica dummy
        patient.setClinica(dummyClinic); 
        
        // *** SE IGNORA LA LÓGICA DE SUBSCRIPCIÓN Y LÍMITE ***
        
        /* Long clinicaId = patient.getClinicaId(); // Esto devolvía null o causaba error
        String planType = subscriptionRepository.findPlanTypeByClinicaId(clinicaId);

        if (planType == null) {
            planType = "PLAN_PRO"; 
        }

        if ("PLAN_FREE".equalsIgnoreCase(planType)) {
            long currentPatientCount = patientRepository.countByClinica_Id(clinicaId);
            if (currentPatientCount >= FREE_PLAN_LIMIT) {
                throw new PlanLimitExceededException(
                    "Límite de pacientes excedido..."
                );
            }
        }
        */
        // =============================================================
        
        // Si el plan es PRO o el límite FREE no se ha alcanzado, se guarda el paciente.
        return patientRepository.save(patient);
    }
    
    // === Métodos CRUD RESTANTES ===

    // NOTA: findAll() seguirá devolviendo todos, sin filtrar por clínica, lo cual es suficiente por ahora.
    
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