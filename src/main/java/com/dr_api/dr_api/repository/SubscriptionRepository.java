package com.dr_api.dr_api.repository;

import com.dr_api.dr_api.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    
    @Query("SELECT s.plan.tipoPlan FROM Subscription s WHERE s.clinica.id = :clinicaId AND s.estado = 'ACTIVA'")
    String findPlanTypeByClinicaId(@Param("clinicaId") Long clinicaId);
}