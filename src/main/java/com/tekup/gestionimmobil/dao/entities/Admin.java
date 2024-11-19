package com.tekup.gestionimmobil.dao.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Admin extends User {

    public Admin() {
        super();
        this.setRole("admin");
    }

    public Admin(Long cin, String nom, String prenom, String email, String password) {
        super(cin, nom, prenom, email, password, "admin");
    }
}