package com.dr_api.dr_api.service;

import com.dr_api.dr_api.model.Contacto;
import java.util.List;
import java.util.Optional;

public interface ContactoService {
  
  // Soluciona el error: save(Contacto) is undefined
  Contacto save(Contacto contacto); 
  
  // Soluciona el error: findAll() is undefined
  List<Contacto> findAll();
  
  // Soluciona el error: findById(Long) is undefined
  Optional<Contacto> findById(Long id);
  
  // Soluciona el error: deleteById(Long) is undefined
  void deleteById(Long id);
}