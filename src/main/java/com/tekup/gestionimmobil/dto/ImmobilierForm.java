package com.tekup.gestionimmobil.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ImmobilierForm {
    private Long id;
    private int prix;
    private String ville;
    private String delegation;
    private int nbPieces;
    private String description;
    private MultipartFile photo; // Change to MultipartFile
    private int surface;
    private int telContact;
    private String type;
    private String etat;
}