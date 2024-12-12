package com.tekup.gestionimmobil.dao.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("Maison")
@NoArgsConstructor
@Setter
@Getter
public class Maison extends Immobilier {

    @Column(name = "nb_chambre", nullable = false)
    private int nbChambre;
}