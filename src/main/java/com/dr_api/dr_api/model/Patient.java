package com.dr_api.dr_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;

@Entity
@Table(name = "PACIENTE")
public class Patient {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PACIENTE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLINICA_ID", nullable = false)
    @JsonIgnore  // Ignora completamente esta relación en JSON
    private Clinic clinica;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PREVISION_ID")
    @JsonIgnore  // Ignora completamente esta relación en JSON
    private Prevision prevision;

    @NotBlank
    @Column(name = "RUT_PACIENTE", unique = true, nullable = false)
    private String rutPaciente;

    @NotBlank
    @Column(name = "NOMBRE_PACIENTE", nullable = false)
    private String nombrePaciente;

    @NotBlank
    @Column(name = "APELLIDO_PACIENTE", nullable = false)
    private String apellidoPaciente;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_NACIMIENTO")
    private Date fechaNacimiento;

    @Column(name = "GENERO")
    private String genero;

    @Column(name = "TELEFONO")
    private String telefono;

    @Column(name = "DIRECCION_PACIENTE")
    private String direccionPaciente;

    // Constructores
    public Patient() {
    }

    public Patient(Clinic clinica, String rutPaciente, String nombrePaciente, String apellidoPaciente) {
        this.clinica = clinica;
        this.rutPaciente = rutPaciente;
        this.nombrePaciente = nombrePaciente;
        this.apellidoPaciente = apellidoPaciente;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore  // También ignora el getter
    public Clinic getClinica() {
        return clinica;
    }
    
    // Este método SÍ se incluye en JSON para enviar el ID de la clínica
    public Long getClinicaId() {
        return this.clinica != null ? this.clinica.getId() : null;
    }

    public void setClinica(Clinic clinica) {
        this.clinica = clinica;
    }

    @JsonIgnore  // También ignora el getter
    public Prevision getPrevision() {
        return prevision;
    }
    
    // Opcionalmente agrega este método para enviar el ID de la previsión
    public Long getPrevisionId() {
        return this.prevision != null ? this.prevision.getId() : null;
    }

    public void setPrevision(Prevision prevision) {
        this.prevision = prevision;
    }

    public String getRutPaciente() {
        return rutPaciente;
    }

    public void setRutPaciente(String rutPaciente) {
        this.rutPaciente = rutPaciente;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getApellidoPaciente() {
        return apellidoPaciente;
    }

    public void setApellidoPaciente(String apellidoPaciente) {
        this.apellidoPaciente = apellidoPaciente;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccionPaciente() {
        return direccionPaciente;
    }

    public void setDireccionPaciente(String direccionPaciente) {
        this.direccionPaciente = direccionPaciente;
    }
}