package com.tekup.gestionimmobil.dao.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "immobiliers")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Immobilier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prix", nullable = false)
    private int prix;

    @Column(name = "ville", nullable = false)
    private String ville;

    @Column(name = "delegation", nullable = false)
    private String delegation;

    @Column(name = "nb_pieces", nullable = false)
    private int nbPieces;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "photo")
    private String photo;

    @Column(name = "surface", nullable = false)
    private int surface;

    @Column(name = "tel_contact", nullable = false)
    private int telContact;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "etat", nullable = false)
    private String etat;
}