package com.tekup.gestionimmobil.web.Controllers;

import com.tekup.gestionimmobil.business.services.AnnonceService;
import com.tekup.gestionimmobil.business.services.ImmobilierService;
import com.tekup.gestionimmobil.dao.entities.Annonce;
import com.tekup.gestionimmobil.dao.entities.Immobilier;
import com.tekup.gestionimmobil.dto.AnnonceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/annonces")
public class AdminController {
    @Autowired
    private AnnonceService annonceService;

    @Autowired
    private ImmobilierService immobilierService;

    @GetMapping
    public String getAllAnnonces(Model model) {
        List<Annonce> annonces = annonceService.findAll();
        model.addAttribute("annonces", annonces);
        return "annonceList";
    }

    @GetMapping("/new")
    public String showAddAnnonceForm(Model model) {
        List<Immobilier> immobiliers = immobilierService.findAll();
        model.addAttribute("immobiliers", immobiliers);
        model.addAttribute("annonceForm", new AnnonceForm());
        return "AjoutAnnonce";
    }

    @PostMapping
    public String createAnnonce(@ModelAttribute AnnonceForm annonceForm) {
        Optional<Immobilier> immobilier = immobilierService.findById(annonceForm.getImmobilierId());
        if (immobilier.isPresent()) {
            Annonce annonce = new Annonce();
            annonce.setImmobilier(immobilier.get());
            annonce.setDate(java.sql.Date.valueOf(LocalDate.now()));
            annonce.setEtatAnnonce("A vendre");
            annonceService.save(annonce);
            return "redirect:/admin/annonces";
        } else {
            return "redirect:/admin/annonces";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditAnnonceForm(@PathVariable Long id, Model model) {
        Optional<Annonce> annonce = annonceService.findById(id);
        if (annonce.isPresent()) {
            AnnonceForm annonceForm = new AnnonceForm();
            Annonce existingAnnonce = annonce.get();
            annonceForm.setId(existingAnnonce.getId());
            annonceForm.setImmobilierId(existingAnnonce.getImmobilier().getId());
            annonceForm.setDate(existingAnnonce.getDate());
            annonceForm.setEtatAnnonce(existingAnnonce.getEtatAnnonce());
            model.addAttribute("annonceForm", annonceForm);
            return "AjoutAnnonce";
        } else {
            return "redirect:/admin/annonces";
        }
    }

    @PostMapping("/{id}")
    public String updateAnnonce(@PathVariable Long id, @ModelAttribute AnnonceForm annonceForm) {
        Optional<Annonce> annonce = annonceService.findById(id);
        if (annonce.isPresent()) {
            Annonce updatedAnnonce = annonce.get();
            Optional<Immobilier> immobilier = immobilierService.findById(annonceForm.getImmobilierId());
            if (immobilier.isPresent()) {
                updatedAnnonce.setImmobilier(immobilier.get());
                updatedAnnonce.setDate(java.sql.Date.valueOf(LocalDate.now()));
                updatedAnnonce.setEtatAnnonce("A vendre");
                annonceService.save(updatedAnnonce);
                return "redirect:/admin/annonces";
            } else {
                return "redirect:/admin/annonces";
            }
        } else {
            return "redirect:/admin/annonces";
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnonce(@PathVariable Long id) {
        annonceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}