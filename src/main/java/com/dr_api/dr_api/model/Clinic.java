package com.dr_api.dr_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CLINICA")
public class Clinic {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLINICA_ID")
    private Long id;

    @Column(name = "NOMBRE_CLINICA")
    private String nombreClinica;
    
    @Column(name = "RUT_CLINICA", unique = true, nullable = false)
    private String rutClinica;

    public Clinic() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // --- AGREGAMOS LOS GETTERS Y SETTERS FALTANTES ---
    public String getNombreClinica() {
        return nombreClinica;
    }

    public void setNombreClinica(String nombreClinica) {
        this.nombreClinica = nombreClinica;
    }

    public String getRutClinica() {
        return rutClinica;
    }

    public void setRutClinica(String rutClinica) {
        this.rutClinica = rutClinica;
    }
}