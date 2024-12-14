package com.tekup.gestionimmobil.dao.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("Terrain")
@Getter
@Setter
@NoArgsConstructor
public class Terrain extends Immobilier {

    // Terrain-specific fields and methods can be added here

    // Example:
    // @Column(name = "categorie")
    // private String categorie;
}