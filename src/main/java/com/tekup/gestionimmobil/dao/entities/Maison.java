package com.tekup.gestionimmobil.dao.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("Maison")
@Getter
@Setter
@NoArgsConstructor
public class Maison extends Immobilier {

    @Column(name = "nb_chambre")
    private Integer nbChambre;

    // Additional Maison-specific fields and methods can be added here
}