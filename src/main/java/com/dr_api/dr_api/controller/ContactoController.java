package com.dr_api.dr_api.controller;

import com.dr_api.dr_api.model.Contacto;
import com.dr_api.dr_api.service.ContactoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contacto") // Endpoint base: http://localhost:8080/api/contacto
@CrossOrigin(origins = "http://localhost:5173") // PERMITE ACCESO DESDE REACT
public class ContactoController {

    private final ContactoService contactoService;

    // Inyección de dependencia a través del constructor (recomendado)
    public ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    // ===============================================
    // 1. GUARDAR CONTACTO (POST)
    // Usado por el formulario de Contacto.jsx
    // ===============================================
    @PostMapping
    public ResponseEntity<Contacto> create(@Valid @RequestBody Contacto contacto) {
        // @Valid asegura que las anotaciones de validación en el modelo (NotBlank, Email) se apliquen.
        Contacto newContacto = contactoService.save(contacto);
        // Retorna el objeto creado con código HTTP 201 (Created)
        return new ResponseEntity<>(newContacto, HttpStatus.CREATED);
    }
    
    // ===============================================
    // 2. OBTENER TODOS LOS CONTACTOS (GET)
    // Útil para un panel de administración
    // ===============================================
    @GetMapping
    public List<Contacto> getAll() {
        return contactoService.findAll();
    }
    
    // ===============================================
    // 3. ELIMINAR CONTACTO (DELETE)
    // Opcional: para limpiar la bandeja de contactos
    // ===============================================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        // Verifica si existe antes de eliminar
        if (contactoService.findById(id).isPresent()) {
            contactoService.deleteById(id);
            // Retorna código 200 (OK) sin contenido
            return ResponseEntity.ok().build();
        }
        // Retorna código 404 (Not Found)
        return ResponseEntity.notFound().build();
    }
}