package com.dr_api.dr_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PREVISION")
public class Prevision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PREVISION_ID")
    private Long id;

    @Column(name = "TIPO_PREVISION", nullable = false)
    private String tipoPrevision; // FONASA, ISAPRE, PARTICULAR

    public Prevision() {}
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTipoPrevision() { return tipoPrevision; }
    public void setTipoPrevision(String tipoPrevision) { this.tipoPrevision = tipoPrevision; }
}