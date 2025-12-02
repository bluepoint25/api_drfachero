package com.dr_api.dr_api.config;

import com.dr_api.dr_api.model.Clinic;
import com.dr_api.dr_api.repository.ClinicRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private final ClinicRepository clinicRepository;

    public DataInitializer(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    @Override
    public void run(String... args) {
        try {
            // Verificamos si la tabla está vacía
            if (clinicRepository.count() == 0) {
                logger.info(">>> [INIT] La tabla CLINICA está vacía. Intentando crear clínica por defecto...");
                
                Clinic clinica = new Clinic();
                clinica.setNombreClinica("Clínica Dr. Fachero Central");
                
                // --- CORRECCIÓN CRÍTICA ---
                // El RUT debe tener PUNTOS y GUION.
                // Si pones solo números, Oracle rechazará el guardado.
                clinica.setRutClinica("77.123.456-7"); 
                
                clinicRepository.save(clinica);
                logger.info(">>> [INIT] ¡ÉXITO! Clínica creada correctamente.");
            } else {
                logger.info(">>> [INIT] El sistema ya tiene clínicas registradas. Todo en orden.");
            }
        } catch (Exception e) {
            // Este log nos dirá exactamente por qué falla si vuelve a ocurrir
            logger.error(">>> [ERROR FATAL] No se pudo crear la clínica inicial. Causa: " + e.getMessage());
        }
    }
}