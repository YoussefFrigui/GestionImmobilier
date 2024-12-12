package com.tekup.gestionimmobil.business.implservices;

import com.tekup.gestionimmobil.business.services.AnnonceService;
import com.tekup.gestionimmobil.dao.entities.Annonce;
import com.tekup.gestionimmobil.dao.repositories.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return annonceRepository.save(annonce);
    }

    @Override
    public void deleteById(Long id) {
        annonceRepository.deleteById(id);
    }
}