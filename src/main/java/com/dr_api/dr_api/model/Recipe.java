package com.dr_api.dr_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "RECIPE")
@SequenceGenerator(
    name = "SEQ_RECIPE",
    sequenceName = "SEQ_RECIPE", 
    allocationSize = 1 
)
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RECIPE")
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "PATIENT_NAME", nullable = false, length = 100)
    private String patientName; 

    @Column(name = "RECIPE_DATE")
    private String date;

    @NotBlank
    @Column(name = "DIAGNOSIS")
    private String diagnosis;

    @NotBlank
    @Column(name = "MEDICAMENT")
    private String medicament;

    @NotBlank
    @Column(name = "QUANTITY")
    private String quantity;

    @Column(name = "DURATION")
    private String duration;

    @NotBlank
    @Column(name = "PRESCRIPTION_DETAIL")
    private String prescriptionDetail;

    public Recipe() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getMedicament() { return medicament; }
    public void setMedicament(String medicament) { this.medicament = medicament; }
    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public String getPrescriptionDetail() { return prescriptionDetail; }
    public void setPrescriptionDetail(String prescriptionDetail) { this.prescriptionDetail = prescriptionDetail; }
}