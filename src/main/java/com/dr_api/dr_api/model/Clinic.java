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
    
    // ... otros getters y setters
}