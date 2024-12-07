package com.tekup.gestionimmobil.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "immobilier_id", nullable = false)
    private Immobilier immobilier;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "etat_annonce", nullable = false)
    private String etatAnnonce;
}