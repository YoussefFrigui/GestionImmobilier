package com.tekup.gestionimmobil.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ImmobilierForm {
    private Long id;
    private int prix;
    private String ville;
    private String delegation;
    private String description;
    private int surface;
    private String contact;
    private String etat;
    private String type; // "Maison" or "Terrain"
    private int nbChambre; // For Maison
    private List<MultipartFile> photos; // Changed to List for multiple uploads
}