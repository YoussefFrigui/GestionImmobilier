package com.tekup.gestionimmobil.dao.repositories;

import com.tekup.gestionimmobil.dao.entities.Annonce;
import com.tekup.gestionimmobil.dao.entities.Annonce.EtatAnnonce;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
    List<Annonce> findByEtatAnnonce(EtatAnnonce etatAnnonce);
    
    // Updated method to navigate through 'immobilier'
    Page<Annonce> findByImmobilier_VilleContainingOrImmobilier_DelegationContaining(String ville, String delegation, Pageable pageable);
}