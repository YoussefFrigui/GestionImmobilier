package com.tekup.gestionimmobil.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "annonces")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Association with Immobilier
    @ManyToOne
    @JoinColumn(name = "immobilier_id", nullable = false)
    private Immobilier immobilier;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat_annonce", nullable = false)
    private EtatAnnonce etatAnnonce;

    // Enum for EtatAnnonce
    public enum EtatAnnonce {
        DISPO("Dispo"),
        INDISPO("Indispo");

        private final String displayName;

        EtatAnnonce(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}