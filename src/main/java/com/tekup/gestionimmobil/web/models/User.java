package com.tekup.gestionimmobil.web.models;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    public Long cin;
    public String nom;
    public String prenom;
    public String email;
    public String password;
    public String role;


}