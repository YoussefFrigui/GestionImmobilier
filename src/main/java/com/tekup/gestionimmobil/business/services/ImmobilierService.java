package com.tekup.gestionimmobil.business.services;

import com.tekup.gestionimmobil.dao.entities.Immobilier;

import java.util.List;
import java.util.Optional;

public interface ImmobilierService {
    List<Immobilier> findAll();
    Optional<Immobilier> findById(Long id);
    Immobilier save(Immobilier immobilier);
    void deleteById(Long id);
    
}