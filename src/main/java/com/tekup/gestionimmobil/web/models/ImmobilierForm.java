package com.tekup.gestionimmobil.web.models;

import com.tekup.gestionimmobil.dao.entities.Immobilier.Delegation;
import com.tekup.gestionimmobil.dao.entities.Immobilier.Etat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ImmobilierForm {
    private Long id;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    @NotNull(message = "Prix is required")
    @Min(value = 0, message = "Prix must be positive")
    private Double prix;
    
    @NotBlank(message = "Ville is required")
    private String ville;
    
    @NotNull(message = "Delegation is required")
    private Delegation delegation;
    
    @NotNull(message = "Surface is required")
    @Min(value = 0, message = "Surface must be positive")
    private Double surface;
    
    @NotBlank(message = "Contact is required")
    private String contact;
    
    @NotNull(message = "Etat is required")
    private Etat etat;
    
    @NotBlank(message = "Type is required")
    private String type;
    
    private Integer nbChambre;
    
    private List<MultipartFile> photos;
}