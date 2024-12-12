package com.tekup.gestionimmobil.dao.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Client extends User {

    public Client(String username, String email, String password) {
        super(username, email, password, "client");
    }
}
