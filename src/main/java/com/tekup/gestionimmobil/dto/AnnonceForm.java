package com.tekup.gestionimmobil.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class AnnonceForm {
    private Long id;
    private Long immobilierId; // Reference to the immobilier
    private Date date;
    private String etatAnnonce;
}