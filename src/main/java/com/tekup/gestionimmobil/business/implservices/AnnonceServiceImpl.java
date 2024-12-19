package com.tekup.gestionimmobil.business.implservices;

import com.tekup.gestionimmobil.business.services.AnnonceService;
import com.tekup.gestionimmobil.dao.entities.Annonce;
import com.tekup.gestionimmobil.dao.entities.Annonce.EtatAnnonce;
import com.tekup.gestionimmobil.dao.repositories.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class AnnonceServiceImpl implements AnnonceService {

    @Autowired
    private AnnonceRepository annonceRepository;

    @Override
    public List<Annonce> findAll() {
        return annonceRepository.findAll();
    }

    @Override
    public Optional<Annonce> findById(Long id) {
        return annonceRepository.findById(id);
    }

    @Override
    public Annonce save(Annonce annonce) {
        if (annonce.getDate() == null) {
            annonce.setDate(new Date());
        }
        return annonceRepository.save(annonce);
    }

    @Override
    public void deleteById(Long id) {
        annonceRepository.deleteById(id);
    }

    @Override
    public List<Annonce> findByEtatAnnonce(EtatAnnonce etatAnnonce) {
        return annonceRepository.findByEtatAnnonce(etatAnnonce);
    }

    @Override
    public Page<Annonce> findPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return annonceRepository.findAll(pageable);
    }

    @Override
    public Page<Annonce> findAnnoncesWithFilters(
            int page, int size, String type, String ville, 
            Double minPrix, Double maxPrix, EtatAnnonce etatAnnonce) {
            
        Pageable pageable = PageRequest.of(page, size);
        
        // Handle null etatAnnonce
        if (etatAnnonce == null) {
            etatAnnonce = EtatAnnonce.DISPO;
        }
        
        // If no filters are applied, return only by etatAnnonce
        if (type == null && ville == null && minPrix == null && maxPrix == null) {
            return annonceRepository.findByEtatAnnonce(etatAnnonce, pageable);
        }

        // Clean up ville parameter
        ville = ville != null ? ville.trim() : null;
        
        // Validate price range
        if (minPrix != null && maxPrix != null && minPrix > maxPrix) {
            Double temp = minPrix;
            minPrix = maxPrix;
            maxPrix = temp;
        }

        return annonceRepository.findByFilters(type, ville, minPrix, maxPrix, etatAnnonce, pageable);
    }
    @Override
public boolean existsByImmobilierId(Long immobilierId) {
    return annonceRepository.existsByImmobilier_Id(immobilierId);
}
}