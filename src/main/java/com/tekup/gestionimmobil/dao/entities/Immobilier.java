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

    public enum Delegation {
        TUNIS("Tunis"),
        ARIANA("Ariana"),
        BEN_AROUS("Ben Arous"),
        MANOUBA("Manouba"),
        NABEUL("Nabeul"),
        ZAGHOUAN("Zaghouan"),
        BIZERTE("Bizerte"),
        BEJA("Béja"),
        JENDOUBA("Jendouba"),
        KEF("Kef"),
        SILIANA("Siliana"),
        SOUSSE("Sousse"),
        MONASTIR("Monastir"),
        MAHDIA("Mahdia"),
        SFAX("Sfax"),
        KAIROUAN("Kairouan"),
        KASSERINE("Kasserine"),
        SIDI_BOUZID("Sidi Bouzid"),
        GABES("Gabès"),
        MEDENINE("Médenine"),
        TATAOUINE("Tataouine"),
        GAFSA("Gafsa"),
        TOZEUR("Tozeur"),
        KEBILI("Kébili");

        private final String displayName;

        Delegation(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum Etat {
        A_VENDRE("A Vendre"),
        A_LOUER("A Louer");

        private final String displayName;

        Etat(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private double prix;
    private String ville;
    
    @Enumerated(EnumType.STRING)
    private Delegation delegation;
    
    private double surface;
    private String contact;
    
    @Enumerated(EnumType.STRING)
    private Etat etat;
    
    private String type;

    @ElementCollection
    @CollectionTable(name = "immobilier_photos", joinColumns = @JoinColumn(name = "immobilier_id"))
    @Column(name = "photo_path")
    private List<String> photoPaths;
}