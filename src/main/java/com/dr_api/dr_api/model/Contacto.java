package com.dr_api.dr_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "CONTACTO")
@SequenceGenerator(
    name = "SEQ_CONTACTO",
    sequenceName = "SEQ_CONTACTO", 
    allocationSize = 1 
)
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONTACTO")
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "La clínica es obligatoria")
    private String clinica;

    @NotNull(message = "El número de profesionales es obligatorio")
    private Integer profesionales;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email incorrecto")
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    private String pais;

    @NotBlank(message = "El mensaje es obligatorio")
    @Column(length = 1000)
    private String mensaje;

    public Contacto() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getClinica() { return clinica; }
    public void setClinica(String clinica) { this.clinica = clinica; }
    public Integer getProfesionales() { return profesionales; }
    public void setProfesionales(Integer profesionales) { this.profesionales = profesionales; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}