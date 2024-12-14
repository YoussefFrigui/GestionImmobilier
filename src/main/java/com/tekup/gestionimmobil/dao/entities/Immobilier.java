package com.tekup.gestionimmobil.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "immobiliers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Immobilier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private double prix;
    private String ville;
    private String delegation;
    private double surface;
    private String contact;
    private String etat;
    private String type;

    // List of photo paths
    @ElementCollection
    @CollectionTable(name = "immobilier_photos", joinColumns = @JoinColumn(name = "immobilier_id"))
    @Column(name = "photo_path")
    private List<String> photoPaths;
}