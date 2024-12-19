package com.tekup.gestionimmobil.web.controllers;

import com.tekup.gestionimmobil.dao.entities.Annonce;
import com.tekup.gestionimmobil.dao.entities.Annonce.EtatAnnonce;
import com.tekup.gestionimmobil.dao.entities.Immobilier;
import com.tekup.gestionimmobil.business.services.AnnonceService;
import com.tekup.gestionimmobil.business.services.ImmobilierService;
import com.tekup.gestionimmobil.web.models.AnnonceForm;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.validation.BindingResult;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Controller
@RequestMapping("/admin/annonces")
public class AnnonceController {

    @Autowired
    private AnnonceService annonceService;

    @Autowired
    private ImmobilierService immobilierService;

    @GetMapping
    public String listAnnonces(Model model) {
        model.addAttribute("annonces", annonceService.findAll());
        return "adminAnnonces";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        if (!model.containsAttribute("annonceForm")) {
            AnnonceForm form = new AnnonceForm();
            form.setEtatAnnonce(EtatAnnonce.DISPO);
            model.addAttribute("annonceForm", form);
        }
        model.addAttribute("immobiliers", immobilierService.findAll());
        return "AjoutAnnonce";
    }

    @PostMapping("/add")
    public String addAnnonce(
            @Valid @ModelAttribute("annonceForm") AnnonceForm form,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            model.addAttribute("immobiliers", immobilierService.findAll());
            return "AjoutAnnonce";
        }

        try {
            // Check if immobilier exists
            Optional<Immobilier> immobilier = immobilierService.findById(form.getImmobilierId());
            if (!immobilier.isPresent()) {
                throw new RuntimeException("Selected property not found");
            }

            // Check if annonce already exists for this immobilier
            if (annonceService.existsByImmobilierId(form.getImmobilierId())) {
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "An announcement already exists for this property");
                redirectAttributes.addFlashAttribute("annonceForm", form);
                return "redirect:/admin/annonces/add";
            }

            // Create new annonce
            Annonce annonce = new Annonce();
            annonce.setImmobilier(immobilier.get());
            annonce.setDate(new Date());
            annonce.setEtatAnnonce(form.getEtatAnnonce());

            // Save annonce
            annonceService.save(annonce);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Announcement created successfully");
            return "redirect:/admin/annonces";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Error creating announcement: " + e.getMessage());
            redirectAttributes.addFlashAttribute("annonceForm", form);
            return "redirect:/admin/annonces/add";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteAnnonce(@PathVariable Long id, 
            RedirectAttributes redirectAttributes) {
        try {
            annonceService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Announcement deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Error deleting announcement");
        }
        return "redirect:/admin/annonces";
    }
}