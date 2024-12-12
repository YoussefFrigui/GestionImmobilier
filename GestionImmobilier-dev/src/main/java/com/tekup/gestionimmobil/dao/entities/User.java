package com.tekup.gestionimmobil.dao.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cin;

    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String role;

    // Constructeur avec tous les paramètres nécessaires
    public User(Long cin, String nom, String prenom, String email, String password, String role) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
