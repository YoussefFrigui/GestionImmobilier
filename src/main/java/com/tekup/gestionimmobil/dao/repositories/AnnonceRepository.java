package com.tekup.gestionimmobil.dao.repositories;

import com.tekup.gestionimmobil.dao.entities.Annonce;
import com.tekup.gestionimmobil.dao.entities.Annonce.EtatAnnonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
    List<Annonce> findByEtatAnnonce(EtatAnnonce etatAnnonce);
}