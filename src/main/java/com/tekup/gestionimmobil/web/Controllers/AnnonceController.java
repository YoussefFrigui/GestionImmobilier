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
            Optional<Immobilier> immobilier = immobilierService.findById(form.getImmobilierId());
            if (!immobilier.isPresent()) {
                throw new RuntimeException("Selected Immobilier not found");
            }

            Annonce annonce = new Annonce();
            annonce.setImmobilier(immobilier.get());
            annonce.setDate(new Date()); // Current system date
            annonce.setEtatAnnonce(form.getEtatAnnonce());

            annonceService.save(annonce);
            redirectAttributes.addFlashAttribute("successMessage", "Annonce added successfully");
            return "redirect:/admin/annonces";
            
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("immobiliers", immobilierService.findAll());
            return "AjoutAnnonce";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Annonce> annonce = annonceService.findById(id);
        if (!annonce.isPresent()) {
            return "redirect:/admin/annonces";
        }

        AnnonceForm form = new AnnonceForm();
        form.setId(annonce.get().getId());
        form.setImmobilierId(annonce.get().getImmobilier().getId());
        form.setEtatAnnonce(annonce.get().getEtatAnnonce());

        model.addAttribute("annonceForm", form);
        model.addAttribute("immobiliers", immobilierService.findAll());
        return "AjoutAnnonce";
    }

    @PostMapping("/toggle/{id}")
    public String toggleEtatAnnonce(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Annonce> optAnnonce = annonceService.findById(id);
            if (optAnnonce.isPresent()) {
                Annonce annonce = optAnnonce.get();
                annonce.setEtatAnnonce(annonce.getEtatAnnonce() == EtatAnnonce.DISPO ? 
                    EtatAnnonce.INDISPO : EtatAnnonce.DISPO);
                annonceService.save(annonce);
                redirectAttributes.addFlashAttribute("successMessage", "Annonce status updated");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating annonce status");
        }
        return "redirect:/admin/annonces";
    }

    @PostMapping("/delete/{id}")
    public String deleteAnnonce(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            annonceService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Annonce deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting annonce");
        }
        return "redirect:/admin/annonces";
    }
}