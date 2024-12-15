package com.tekup.gestionimmobil.business.services;

import com.tekup.gestionimmobil.dao.entities.Annonce;
import com.tekup.gestionimmobil.dao.entities.Annonce.EtatAnnonce;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface AnnonceService {
    List<Annonce> findAll();
    Optional<Annonce> findById(Long id);
    Annonce save(Annonce annonce);
    void deleteById(Long id);
    List<Annonce> findByEtatAnnonce(EtatAnnonce etatAnnonce);
    Page<Annonce> findPaginated(int page, int size);
    Page<Annonce> findAnnoncesWithFilters(int page, int size, String type, 
        String ville, Double minPrix, Double maxPrix, EtatAnnonce etatAnnonce);
}