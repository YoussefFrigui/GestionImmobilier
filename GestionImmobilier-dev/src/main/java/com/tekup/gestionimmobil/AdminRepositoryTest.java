package com.tekup.gestionimmobil;

import com.tekup.gestionimmobil.dao.entities.Admin;
import com.tekup.gestionimmobil.*;l.dao.repositories.AdminRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    public void testSaveAdmin() {
        // Créer un admin
        Admin admin = new Admin(12345L, "John", "Doe", "admin@example.com", "securepassword");
        
        // Sauvegarder l'admin
        Admin savedAdmin = adminRepository.save(admin);

        // Vérifiez que l'admin a bien été sauvegardé
        Assertions.assertNotNull(savedAdmin);
        Assertions.assertEquals("John", savedAdmin.getNom());
        Assertions.assertEquals("Doe", savedAdmin.getPrenom());
        Assertions.assertEquals("admin@example.com", savedAdmin.getEmail());

        System.out.println("Admin saved successfully: " + savedAdmin.getNom());
    }
}
