package com.dr_api.dr_api.service.impl;

import com.dr_api.dr_api.model.Contacto;
import com.dr_api.dr_api.repository.ContactoRepository;
import com.dr_api.dr_api.service.ContactoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ContactoServiceImpl implements ContactoService {

  private final ContactoRepository repo;

  public ContactoServiceImpl(ContactoRepository repo) {
    this.repo = repo;
  }

  @Override
  @Transactional
  public Contacto save(Contacto contacto) { // Implementación de save
    return repo.save(contacto);
  }
  
  @Override
  public List<Contacto> findAll() { // Implementación de findAll
    return repo.findAll();
  }
  
  @Override
  public Optional<Contacto> findById(Long id) { // Implementación de findById
    return repo.findById(id);
  }

  @Override
  @Transactional
  public void deleteById(Long id) { // Implementación de deleteById
    repo.deleteById(id);
  }
}