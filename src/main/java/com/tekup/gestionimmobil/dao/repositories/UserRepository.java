package com.tekup.gestionimmobil.dao.repositories;

import com.tekup.gestionimmobil.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}