package com.tekup.gestionimmobil.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientForm {

    private Long cin;
    private String nom;
    private String prenom;
    private String username;
    private String email;
    private String password;

    public ClientForm(Long cin, String nom, String prenom, String email, String password) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
    }
}
