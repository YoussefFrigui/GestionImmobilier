package com.tekup.gestionimmobil.web.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ImmobilierForm {
    private Long id;
    private double prix;
    private String ville;
    private String delegation;
    private String description;
    private double surface;
    private String contact;
    private String etat; // Will map to Enum in the controller
    private String type; // "Maison" or "Terrain"
    private Integer nbChambre; // For Maison; use Integer to allow null
    private List<MultipartFile> photos; // For multiple photo uploads
}