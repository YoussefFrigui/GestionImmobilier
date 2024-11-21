package com.tekup.gestionimmobil.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ClientForm {

    @NotNull
    private Long cin;

    @NotBlank
    private String nom;

    @NotBlank
    private String prenom;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String role = "client";

    // Custom constructor without role
    public ClientForm(Long cin, String nom, String prenom, String email, String password) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
    }
}