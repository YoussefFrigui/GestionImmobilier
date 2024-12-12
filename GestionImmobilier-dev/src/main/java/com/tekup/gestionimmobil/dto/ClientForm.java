package com.tekup.gestionimmobil.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ClientForm {

    private String username;
    private String email;
    private String password;

    // Ajoutez un constructeur si nécessaire
    public ClientForm(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
