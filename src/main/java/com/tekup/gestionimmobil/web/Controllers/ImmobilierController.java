package com.tekup.gestionimmobil.web.Controllers;

import com.tekup.gestionimmobil.business.services.ImmobilierService;
import com.tekup.gestionimmobil.dao.entities.Immobilier;
import com.tekup.gestionimmobil.dao.entities.Maison;
import com.tekup.gestionimmobil.dao.entities.Terrain;
import com.tekup.gestionimmobil.dto.ImmobilierForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;

@Controller
@RequestMapping("/admin/immobiliers")
public class ImmobilierController {

    @Autowired
    private ImmobilierService immobilierService;

    private static final String UPLOAD_DIR = "uploads/";

    // Display all Immobiliers
    @GetMapping
    public String getAllImmobiliers(Model model) {
        model.addAttribute("immobiliers", immobilierService.findAll());
        return "immobilierList";
    }

    // Show form to add a new Immobilier
    @GetMapping("/new")
    public String showAddImmobilierForm(Model model) {
        model.addAttribute("immobilierForm", new ImmobilierForm());
        return "immobilierForm";
    }

    // Handle form submission for creating Immobilier
    @PostMapping
    public String createImmobilier(@ModelAttribute ImmobilierForm immobilierForm) {
        Immobilier immobilier;

        if ("Maison".equalsIgnoreCase(immobilierForm.getType())) {
            immobilier = new Maison();
            ((Maison) immobilier).setNbChambre(immobilierForm.getNbChambre());
        } else if ("Terrain".equalsIgnoreCase(immobilierForm.getType())) {
            immobilier = new Terrain();
        } else {
            throw new IllegalArgumentException("Unknown type: " + immobilierForm.getType());
        }

        // Set common fields
        immobilier.setPrix(immobilierForm.getPrix());
        immobilier.setVille(immobilierForm.getVille());
        immobilier.setDelegation(immobilierForm.getDelegation());
        immobilier.setDescription(immobilierForm.getDescription());
        immobilier.setSurface(immobilierForm.getSurface());
        immobilier.setContact(immobilierForm.getContact());
        immobilier.setEtat(immobilierForm.getEtat());

        // Handle multiple file uploads
        if (immobilierForm.getPhotos() != null && !immobilierForm.getPhotos().isEmpty()) {
            for (MultipartFile photo : immobilierForm.getPhotos()) {
                if (!photo.isEmpty()) {
                    try {
                        String filename = System.currentTimeMillis() + "_" + Paths.get(photo.getOriginalFilename()).getFileName().toString();
                        Path path = Paths.get(UPLOAD_DIR + filename);
                        Files.createDirectories(path.getParent());
                        Files.write(path, photo.getBytes());
                        immobilier.getPhotos().add(filename);
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Optional: Add proper error handling
                    }
                }
            }
        }

        immobilierService.save(immobilier);
        return "redirect:/admin/immobiliers";
    }

    // Show form to edit an existing Immobilier
    @GetMapping("/edit/{id}")
    public String showEditImmobilierForm(@PathVariable Long id, Model model) {
        Optional<Immobilier> optImmobilier = immobilierService.findById(id);
        if (optImmobilier.isPresent()) {
            Immobilier existingImmobilier = optImmobilier.get();
            ImmobilierForm immobilierForm = new ImmobilierForm();
            immobilierForm.setId(existingImmobilier.getId());
            immobilierForm.setPrix(existingImmobilier.getPrix());
            immobilierForm.setVille(existingImmobilier.getVille());
            immobilierForm.setDelegation(existingImmobilier.getDelegation());
            immobilierForm.setDescription(existingImmobilier.getDescription());
            immobilierForm.setSurface(existingImmobilier.getSurface());
            immobilierForm.setContact(existingImmobilier.getContact());
            immobilierForm.setEtat(existingImmobilier.getEtat());

            if (existingImmobilier instanceof Maison) {
                Maison maison = (Maison) existingImmobilier;
                immobilierForm.setType("Maison");
                immobilierForm.setNbChambre(maison.getNbChambre());
            } else if (existingImmobilier instanceof Terrain) {
                immobilierForm.setType("Terrain");
            }

            model.addAttribute("immobilierForm", immobilierForm);
            return "immobilierForm";
        } else {
            return "redirect:/admin/immobiliers";
        }
    }

    // Handle form submission for updating Immobilier
    @PostMapping("/{id}")
    public String updateImmobilier(@PathVariable Long id, @ModelAttribute ImmobilierForm immobilierForm) {
        Optional<Immobilier> optImmobilier = immobilierService.findById(id);
        if (optImmobilier.isPresent()) {
            Immobilier updatedImmobilier = optImmobilier.get();
            updatedImmobilier.setPrix(immobilierForm.getPrix());
            updatedImmobilier.setVille(immobilierForm.getVille());
            updatedImmobilier.setDelegation(immobilierForm.getDelegation());
            updatedImmobilier.setDescription(immobilierForm.getDescription());
            updatedImmobilier.setSurface(immobilierForm.getSurface());
            updatedImmobilier.setContact(immobilierForm.getContact());
            updatedImmobilier.setEtat(immobilierForm.getEtat());

            if (updatedImmobilier instanceof Maison) {
                Maison maison = (Maison) updatedImmobilier;
                maison.setNbChambre(immobilierForm.getNbChambre());
            }

            // Handle multiple file uploads
            if (immobilierForm.getPhotos() != null && !immobilierForm.getPhotos().isEmpty()) {
                for (MultipartFile photo : immobilierForm.getPhotos()) {
                    if (!photo.isEmpty()) {
                        try {
                            String filename = System.currentTimeMillis() + "_" + Paths.get(photo.getOriginalFilename()).getFileName().toString();
                            Path path = Paths.get(UPLOAD_DIR + filename);
                            Files.createDirectories(path.getParent());
                            Files.write(path, photo.getBytes());
                            updatedImmobilier.getPhotos().add(filename);
                        } catch (IOException e) {
                            e.printStackTrace();
                            // Optional: Add proper error handling
                        }
                    }
                }
            }

            immobilierService.save(updatedImmobilier);
            return "redirect:/admin/immobiliers";
        } else {
            return "redirect:/admin/immobiliers";
        }
    }

    // Handle deletion of Immobilier
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImmobilier(@PathVariable Long id) {
        immobilierService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}