package com.tekup.gestionimmobil.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "immobiliers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter


public abstract class Immobilier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prix", nullable = false)
    private int prix;

    @Column(name = "ville", nullable = false)
    private String ville;

    @Column(name = "delegation", nullable = false)
    private String delegation;

    @Column(name = "surface", nullable = false)
    private int surface;

    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "description", nullable = false)
    private String description;

    public enum Etat {
        A_VENDRE("A vendre"),
        A_LOUER("A louer");

        private final String displayName;

        Etat(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    @Column(name = "etat", nullable = false)
    private String etat;

    @ElementCollection
    @CollectionTable(name = "immobilier_photos", joinColumns = @JoinColumn(name = "immobilier_id"))
    @Column(name = "photo")
    private List<String> photos = new ArrayList<>();

    }