package com.tekup.gestionimmobil.web.controllers;

import com.tekup.gestionimmobil.business.services.AnnonceService;
import com.tekup.gestionimmobil.dao.entities.Annonce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String viewHomePage(Model model, 
                               @RequestParam(value = "page", defaultValue = "1") int page) {
        int pageSize = 6;
        Page<Annonce> annoncePage = annonceService.findPaginated(page, pageSize);
        model.addAttribute("annonces", annoncePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", annoncePage.getTotalPages());
        return "Acceuil";
    }

    @GetMapping("/signin")
    public String showSignInPage() {
        return "signin"; // Matches signin.html
    }
}