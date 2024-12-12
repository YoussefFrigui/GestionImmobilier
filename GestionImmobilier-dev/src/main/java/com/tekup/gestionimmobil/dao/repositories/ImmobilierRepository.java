package com.tekup.gestionimmobil.dao.repositories;

import com.tekup.gestionimmobil.dao.entities.Immobilier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImmobilierRepository extends JpaRepository<Immobilier, Long> {
}