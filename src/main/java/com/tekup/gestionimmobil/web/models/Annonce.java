package com.tekup.gestionimmobil.web.models;

import lombok.Getter;
import lombok.Setter;
import java.sql.Date;

@Getter
@Setter
public class Annonce extends Immobilier{
    public Date date;
    public String etatAnnonce;

    
}