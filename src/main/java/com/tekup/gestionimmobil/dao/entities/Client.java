package com.tekup.gestionimmobil.dao.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Client extends User {

    public Client(Long cin, String nom, String prenom, String email, String password) {
        super(cin, nom, prenom, email, password, "client");
    }
}