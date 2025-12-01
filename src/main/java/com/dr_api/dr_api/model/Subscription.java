package com.dr_api.dr_api.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SUSCRIPCION")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUSCRIPCION_ID")
    private Long id;

    // Relación Many-to-One con CLINICA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLINICA_ID", nullable = false)
    private Clinic clinica; // Usado en PatientServiceImpl para obtener el ID

    // Relación Many-to-One con PLAN
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAN_ID", nullable = false)
    private Plan plan; // Usado en la consulta JQL para obtener tipoPlan

    @Column(name = "ESTADO", nullable = false)
    private String estado; // Usado para filtrar por 'ACTIVA'

    public Subscription() {}
    
    // Getters esenciales para la lógica
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Clinic getClinica() { return clinica; }
    public void setClinica(Clinic clinica) { this.clinica = clinica; }
    public Plan getPlan() { return plan; }
    public void setPlan(Plan plan) { this.plan = plan; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    // ... otros getters y setters
}