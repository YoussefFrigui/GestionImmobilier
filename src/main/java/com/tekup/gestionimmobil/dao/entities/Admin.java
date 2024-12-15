package com.tekup.gestionimmobil.dao.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Admin   {

    public Admin(Long cin, String nom, String prenom, String email, String password) {
        
    }
}
