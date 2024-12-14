package com.tekup.gestionimmobil.web.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AnnonceForm {
    private Long id;
    private Long immobilierId; // Reference to the Immobilier
    private Date date;
    private String etatAnnonce; // Should match the enum names: DISPO, INDISPO
}