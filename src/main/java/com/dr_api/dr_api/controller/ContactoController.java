package com.dr_api.dr_api.controller;

import com.dr_api.dr_api.model.Contacto;
import com.dr_api.dr_api.service.ContactoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contacto") 
@CrossOrigin(origins = "http://localhost:5173") 
public class ContactoController {

    private final ContactoService contactoService;

    
    public ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }
    @PostMapping
    public ResponseEntity<Contacto> create(@Valid @RequestBody Contacto contacto) {
        Contacto newContacto = contactoService.save(contacto);
        return ResponseEntity.ok(newContacto);
        
    }
    
 
    @GetMapping
    public List<Contacto> getAll() {
        return contactoService.findAll();
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (contactoService.findById(id).isPresent()) {
            contactoService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}