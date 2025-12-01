package com.dr_api.dr_api.service;

import com.dr_api.dr_api.model.Patient;
import java.util.List;
import java.util.Optional;

public interface PatientService {
  List<Patient> findAll();
  Optional<Patient> findById(Long id);
  Patient save(Patient patient);
  void deleteById(Long id);
}