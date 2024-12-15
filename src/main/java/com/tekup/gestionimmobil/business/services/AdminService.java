package com.tekup.gestionimmobil.business.services;

import com.tekup.gestionimmobil.dao.entities.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<Admin> findAll();
    Optional<Admin> findById(Long id);
    Admin save(Admin admin);
    void deleteById(Long id);
}