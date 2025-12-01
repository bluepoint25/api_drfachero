package com.dr_api.dr_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "CONTACTO")
@SequenceGenerator(
    name = "SEQ_CONTACTO",
    sequenceName = "SEQ_CONTACTO", // Asegúrate de que esta secuencia exista en tu DB
    allocationSize = 1 
)
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONTACTO")
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email incorrecto")
    @Size(max = 100, message = "El email no puede exceder los 100 caracteres")
    @Column(name = "EMAIL", nullable = false, length = 100)
    private String email;

    @NotBlank(message = "El asunto es obligatorio")
    @Size(max = 255, message = "El asunto no puede exceder los 255 caracteres")
    @Column(name = "SUBJECT", nullable = false, length = 255)
    private String subject;

    @NotBlank(message = "El mensaje es obligatorio")
    @Column(name = "MESSAGE_BODY", nullable = false) // Usar un nombre de columna que no sea palabra reservada
    private String message;

    public Contacto() {}

    // Getters y Setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}