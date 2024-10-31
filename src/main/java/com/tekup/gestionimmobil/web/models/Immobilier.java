package com.tekup.gestionimmobil.web.models;

public class Immobilier {
    public Long idImmobilier;
    public int prix;
    public String ville;
    public String delegation;
    public int nbPieces;
    public String description;
    public String photo;
    public int surface;
    public int telContact;
    public String type;
    public String etat;
    public Long getIdImmobilier() {
        return idImmobilier;
    }
    public void setIdImmobilier(Long idImmobilier) {
        this.idImmobilier = idImmobilier;
    }
    public int getPrix() {
        return prix;
    }
    public void setPrix(int prix) {
        this.prix = prix;
    }
    public String getVille() {
        return ville;
    }
    public void setVille(String ville) {
        this.ville = ville;
    }
    public String getDelegation() {
        return delegation;
    }
    public void setDelegation(String delegation) {
        this.delegation = delegation;
    }
    public int getNbPieces() {
        return nbPieces;
    }
    public void setNbPieces(int nbPieces) {
        this.nbPieces = nbPieces;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public int getSurface() {
        return surface;
    }
    public void setSurface(int surface) {
        this.surface = surface;
    }
    public int getTelContact() {
        return telContact;
    }
    public void setTelContact(int telContact) {
        this.telContact = telContact;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getEtat() {
        return etat;
    }
    public void setEtat(String etat) {
        this.etat = etat;
    }
    
    
}