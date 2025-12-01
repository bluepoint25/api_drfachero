package com.dr_api.dr_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PLAN")
public class Plan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLAN_ID")
    private Long id;

    @Column(name = "CODIGO", nullable = false)
    private String codigo;
    
    @Column(name = "TIPO_PLAN", nullable = false)
    private String tipoPlan; // Clave: 'PLAN_FREE' o 'PLAN_PRO'

    public Plan() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // ¡CRUCIAL para la consulta JQL!
    public String getTipoPlan() { return tipoPlan; }
    public void setTipoPlan(String tipoPlan) { this.tipoPlan = tipoPlan; }

    // ... otros getters y setters
}