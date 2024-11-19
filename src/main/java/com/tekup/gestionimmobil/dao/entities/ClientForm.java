package com.tekup.gestionimmobil.dao.entities;

import jakarta.validation.constraints.NotBlank;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientForm {
@NotBlank
public Long cin;
@NotBlank
public String nom;
@NotBlank
public String prenom;
@NotBlank
public String email;
@NotBlank
public String password;

public String role = "client";
    
}