package com.tekup.gestionimmobil.business.services;

import com.tekup.gestionimmobil.dao.entities.Annonce;

import java.util.List;
import java.util.Optional;

public interface AnnonceService {
    List<Annonce> findAll();
    Optional<Annonce> findById(Long id);
    Annonce save(Annonce annonce);
    void deleteById(Long id);
}