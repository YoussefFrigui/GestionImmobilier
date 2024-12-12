package com.tekup.gestionimmobil.dao.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("Terrain")
@NoArgsConstructor
@Setter
@Getter
public class Terrain extends Immobilier {
    // No additional fields for Terrain
}