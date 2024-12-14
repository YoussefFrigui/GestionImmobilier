package com.tekup.gestionimmobil.web.controllers;

import com.tekup.gestionimmobil.dao.entities.Annonce;
import com.tekup.gestionimmobil.dao.entities.Annonce.EtatAnnonce;
import com.tekup.gestionimmobil.dao.entities.Immobilier;
import com.tekup.gestionimmobil.business.services.AnnonceService;
import com.tekup.gestionimmobil.business.services.ImmobilierService;
import com.tekup.gestionimmobil.web.models.AnnonceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    /**
     * Display the form to add a new Annonce
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        AnnonceForm annonceForm = new AnnonceForm();
        annonceForm.setDate(new Date());
        annonceForm.setEtatAnnonce(EtatAnnonce.DISPO.name());
        model.addAttribute("annonceForm", annonceForm);

        List<Immobilier> immobiliers = immobilierService.findAll();
        model.addAttribute("immobiliers", immobiliers);

        return "AjoutAnnonce";
    }

    /**
     * Handle the submission of a new Annonce
     */
    @PostMapping("/add")
    public String addAnnonce(@ModelAttribute("annonceForm") AnnonceForm annonceForm,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Please correct the errors in the form.");
            List<Immobilier> immobiliers = immobilierService.findAll();
            model.addAttribute("immobiliers", immobiliers);
            return "AjoutAnnonce";
        }

        Optional<Immobilier> optImmobilier = immobilierService.findById(annonceForm.getImmobilierId());
        if (!optImmobilier.isPresent()) {
            model.addAttribute("error", "Selected Immobilier does not exist.");
            List<Immobilier> immobiliers = immobilierService.findAll();
            model.addAttribute("immobiliers", immobiliers);
            return "AjoutAnnonce";
        }

        Annonce annonce = new Annonce();
        annonce.setImmobilier(optImmobilier.get());
        annonce.setDate(annonceForm.getDate());
        try {
            annonce.setEtatAnnonce(EtatAnnonce.valueOf(annonceForm.getEtatAnnonce()));
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Invalid Etat Annonce selected.");
            List<Immobilier> immobiliers = immobilierService.findAll();
            model.addAttribute("immobiliers", immobiliers);
            return "AjoutAnnonce";
        }

        annonceService.save(annonce);
        return "redirect:/admin/annonces";
    }

    /**
     * List all Annonces for Admin
     */
    @GetMapping
    public String listAnnonces(Model model) {
        List<Annonce> annonces = annonceService.findAll();
        model.addAttribute("annonces", annonces);
        return "adminAnnonces";
    }

    /**
     * Show Edit Annonce Form
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Annonce> optAnnonce = annonceService.findById(id);
        if (optAnnonce.isPresent()) {
            Annonce annonce = optAnnonce.get();
            AnnonceForm annonceForm = new AnnonceForm();
            annonceForm.setId(annonce.getId());
            annonceForm.setImmobilierId(annonce.getImmobilier().getId());
            annonceForm.setDate(annonce.getDate());
            annonceForm.setEtatAnnonce(annonce.getEtatAnnonce().name());

            model.addAttribute("annonceForm", annonceForm);
            List<Immobilier> immobiliers = immobilierService.findAll();
            model.addAttribute("immobiliers", immobiliers);
            return "AjoutAnnonce";
        } else {
            model.addAttribute("error", "Annonce not found.");
            return "redirect:/admin/annonces";
        }
    }

    /**
     * Handle Edit Annonce
     */
    @PostMapping("/edit/{id}")
    public String editAnnonce(@PathVariable Long id,
                              @ModelAttribute("annonceForm") AnnonceForm annonceForm,
                              BindingResult bindingResult,
                              Model model) {
        Optional<Annonce> optAnnonce = annonceService.findById(id);
        if (!optAnnonce.isPresent()) {
            model.addAttribute("error", "Annonce not found.");
            return "redirect:/admin/annonces";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Please correct the errors in the form.");
            List<Immobilier> immobiliers = immobilierService.findAll();
            model.addAttribute("immobiliers", immobiliers);
            return "AjoutAnnonce";
        }

        Optional<Immobilier> optImmobilier = immobilierService.findById(annonceForm.getImmobilierId());
        if (!optImmobilier.isPresent()) {
            model.addAttribute("error", "Selected Immobilier does not exist.");
            List<Immobilier> immobiliers = immobilierService.findAll();
            model.addAttribute("immobiliers", immobiliers);
            return "AjoutAnnonce";
        }

        Annonce annonce = optAnnonce.get();
        annonce.setImmobilier(optImmobilier.get());
        annonce.setDate(annonceForm.getDate());
        try {
            annonce.setEtatAnnonce(EtatAnnonce.valueOf(annonceForm.getEtatAnnonce()));
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Invalid Etat Annonce selected.");
            List<Immobilier> immobiliers = immobilierService.findAll();
            model.addAttribute("immobiliers", immobiliers);
            return "AjoutAnnonce";
        }

        annonceService.save(annonce);
        return "redirect:/admin/annonces";
    }

    /**
     * Toggle EtatAnnonce
     */
    @PostMapping("/toggle/{id}")
    public String toggleEtatAnnonce(@PathVariable Long id) {
        Optional<Annonce> optAnnonce = annonceService.findById(id);
        if (optAnnonce.isPresent()) {
            Annonce annonce = optAnnonce.get();
            if (annonce.getEtatAnnonce() == EtatAnnonce.DISPO) {
                annonce.setEtatAnnonce(EtatAnnonce.INDISPO);
            } else {
                annonce.setEtatAnnonce(EtatAnnonce.DISPO);
            }
            annonceService.save(annonce);
        }
        return "redirect:/admin/annonces";
    }

    /**
     * Delete Annonce
     */
    @PostMapping("/delete/{id}")
    public String deleteAnnonce(@PathVariable Long id) {
        annonceService.deleteById(id);
        return "redirect:/admin/annonces";
    }
}