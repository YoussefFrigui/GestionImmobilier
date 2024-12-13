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

    // Constructor with all necessary parameters
    public User(Long cin, String nom, String prenom, String email, String password, String role) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Constructor with username, email, password, and role
    public User(String username, String email, String password, String role) {
        this.nom = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getter and setter for username
    public String getUsername() {
        return nom;
    }

    public void setUsername(String username) {
        this.nom = username;
    }
}