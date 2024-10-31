package com.tekup.gestionimmobil.web.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Annonce extends Immobilier{
    public Date date;
    public String etatAnnonce;

    
}