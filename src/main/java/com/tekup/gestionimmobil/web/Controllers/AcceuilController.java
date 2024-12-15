package com.tekup.gestionimmobil.web.controllers;

import com.tekup.gestionimmobil.business.services.AnnonceService;
import com.tekup.gestionimmobil.dao.entities.Annonce;
import com.tekup.gestionimmobil.dao.entities.Annonce.EtatAnnonce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AcceuilController {

    @Autowired
    private AnnonceService annonceService;

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/Acceuil";
    }

    @GetMapping("/Acceuil")
    public String viewHomePage(
            Model model,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String ville,
            @RequestParam(required = false) Double minPrix,
            @RequestParam(required = false) Double maxPrix) {

        try {
            // Handle edge cases for price range
            if (minPrix != null && maxPrix != null && minPrix > maxPrix) {
                Double temp = minPrix;
                minPrix = maxPrix;
                maxPrix = temp;
            }

            // Get filtered and paginated announcements
            Page<Annonce> annoncePage = annonceService.findAnnoncesWithFilters(
                page - 1, size, type, ville, minPrix, maxPrix, EtatAnnonce.DISPO);

            // Add pagination data to model
            model.addAttribute("annonces", annoncePage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", annoncePage.getTotalPages());
            model.addAttribute("totalItems", annoncePage.getTotalElements());

            // Add filter parameters to model
            model.addAttribute("type", type);
            model.addAttribute("ville", ville);
            model.addAttribute("minPrix", minPrix);
            model.addAttribute("maxPrix", maxPrix);

            return "Acceuil";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error loading announcements: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/details/{id}")
    public String showAnnonceDetails(
            @PathVariable Long id, 
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            return annonceService.findById(id)
                .map(annonce -> {
                    model.addAttribute("annonce", annonce);
                    return "annonceDetails";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Announcement not found");
                    return "redirect:/Acceuil";
                });
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error loading announcement details");
            return "redirect:/Acceuil";
        }
    }

    @GetMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("errorMessage", "An unexpected error occurred");
        return "error";
    }
}