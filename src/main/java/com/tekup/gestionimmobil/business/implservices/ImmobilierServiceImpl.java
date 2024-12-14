package com.tekup.gestionimmobil.business.implservices;

import com.tekup.gestionimmobil.business.services.ImmobilierService;
import com.tekup.gestionimmobil.dao.entities.Immobilier;
import com.tekup.gestionimmobil.dao.repositories.ImmobilierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImmobilierServiceImpl implements ImmobilierService {

    @Autowired
    private ImmobilierRepository immobilierRepository;

    @Override
    public List<Immobilier> findAll() {
        return immobilierRepository.findAll();
    }

    @Override
    public Optional<Immobilier> findById(Long id) {
        return immobilierRepository.findById(id);
    }

    @Override
    public Immobilier save(Immobilier immobilier) {
        return immobilierRepository.save(immobilier);
    }

    @Override
    public void deleteById(Long id) {
        immobilierRepository.deleteById(id);
    }
}