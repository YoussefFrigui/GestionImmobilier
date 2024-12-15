package com.tekup.gestionimmobil.web.models;

import com.tekup.gestionimmobil.dao.entities.Annonce.EtatAnnonce;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnnonceForm {
    private Long id;
    
    @NotNull(message = "Please select an immobilier")
    private Long immobilierId;
    
    @NotNull(message = "Please select an etat")
    private EtatAnnonce etatAnnonce;
}