package com.tekup.gestionimmobil.web.Controllers;

import com.tekup.gestionimmobil.business.services.AnnonceService;
import com.tekup.gestionimmobil.dao.entities.Annonce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AcceuilController {

    @Autowired
    private AnnonceService annonceService;

    @GetMapping("/")
    public String showAcceuil(Model model) {
        List<Annonce> annonces = annonceService.findAll();
        model.addAttribute("annonces", annonces);
        return "Acceuil";
    }

    @GetMapping("/acceuil")
    public String showAcceuilPage() {
        return "Acceuil"; // Matches Acceuil.html
    }

    @GetMapping("/signin")
    public String showSignInPage() {
        return "signin"; // Matches signin.html
    }
}