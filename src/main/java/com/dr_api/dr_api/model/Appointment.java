package com.dr_api.dr_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "APPOINTMENT")
@SequenceGenerator(
    name = "SEQ_APPOINTMENT",
    sequenceName = "SEQ_APPOINTMENT", 
    allocationSize = 1 
)
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_APPOINTMENT")
    private Long id;

    @NotBlank(message = "El nombre del paciente es obligatorio")
    @Size(max = 100)
    @Column(name = "PATIENT_NAME", nullable = false, length = 100)
    private String patient; // Nombre del paciente

    @NotBlank
    @Column(name = "RUT", length = 20)
    private String rut; 

    @Column(name = "APPOINTMENT_DATE")
    private String date; // Fecha de la cita

    @Column(name = "APPOINTMENT_TIME")
    private String time; // Hora de la cita

    @NotBlank
    @Column(name = "REASON")
    private String reason; // Motivo de la cita

    @Column(name = "STATUS", length = 50)
    private String status; // Confirmada, En espera, Finalizada

    @Column(name = "MEDIC_NAME")
    private String medic; // Médico tratante
    
    @Column(name = "LOCATION")
    private String location = "Clínica Los Andes"; // Ubicación

    public Appointment() {}

    // Getters y Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPatient() { return patient; }
    public void setPatient(String patient) { this.patient = patient; }
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMedic() { return medic; }
    public void setMedic(String medic) { this.medic = medic; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}