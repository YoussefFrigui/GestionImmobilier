package com.tekup.gestionimmobil.web.controllers;

import com.tekup.gestionimmobil.business.services.ImmobilierService;
import com.tekup.gestionimmobil.dao.entities.Immobilier;
import com.tekup.gestionimmobil.dao.entities.Maison;
import com.tekup.gestionimmobil.dao.entities.Terrain;
import com.tekup.gestionimmobil.web.models.ImmobilierForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;

@Controller
@RequestMapping("/admin/immobiliers") // Ensure base mapping is /admin/immobiliers
public class ImmobilierController {

    @Autowired
    private ImmobilierService immobilierService;

    private static final String UPLOAD_DIR = "uploads/";

    // Constructor to create upload directory if it doesn't exist
    public ImmobilierController() {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                e.printStackTrace();
                // Consider logging and handling this exception more gracefully
            }
        }
    }

    /**
     * Display all Immobiliers
     */
    @GetMapping
    public String getAllImmobiliers(Model model) {
        model.addAttribute("immobiliers", immobilierService.findAll());
        return "immobilierList"; // Ensure this template exists
    }

    /**
     * Show form to add a new Immobilier
     */
    @GetMapping("/add")
    public String showAddImmobilierForm(Model model) {
        ImmobilierForm immobilierForm = new ImmobilierForm();
        model.addAttribute("immobilier", immobilierForm); // Consistent attribute name
        return "AjoutImmobilier"; // Ensure this template exists
    }

    /**
     * Handle form submission for creating Immobilier
     */
    @PostMapping("/add")
    public String createImmobilier(@ModelAttribute("immobilier") ImmobilierForm immobilierForm) {
        Immobilier immobilier;

        if ("Maison".equalsIgnoreCase(immobilierForm.getType())) {
            immobilier = new Maison();
            ((Maison) immobilier).setNbChambre(immobilierForm.getNbChambre());
            // Set other Maison-specific fields if any
        } else if ("Terrain".equalsIgnoreCase(immobilierForm.getType())) {
            immobilier = new Terrain();
            // Set Terrain-specific fields if any
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
        immobilier.setType(immobilierForm.getType());

        // Handle multiple file uploads
        if (immobilierForm.getPhotos() != null && !immobilierForm.getPhotos().isEmpty()) {
            for (MultipartFile photo : immobilierForm.getPhotos()) {
                if (!photo.isEmpty()) {
                    try {
                        String filename = System.currentTimeMillis() + "_" + 
                                           Paths.get(photo.getOriginalFilename()).getFileName().toString();
                        Path path = Paths.get(UPLOAD_DIR + filename);
                        Files.createDirectories(path.getParent());
                        Files.write(path, photo.getBytes());
                        immobilier.getPhotoPaths().add("/uploads/" + filename); // Adjust path as needed
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Optional: Add proper error handling, e.g., redirect with error message
                    }
                }
            }
        }

        immobilierService.save(immobilier);
        return "redirect:/admin/immobiliers"; // Redirect to the list page
    }

    /**
     * Show form to edit an existing Immobilier
     */
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
            immobilierForm.setType(existingImmobilier.getType());

            if (existingImmobilier instanceof Maison) {
                Maison maison = (Maison) existingImmobilier;
                immobilierForm.setNbChambre(maison.getNbChambre());
                // Set other Maison-specific fields if any
            } else if (existingImmobilier instanceof Terrain) {
                // Set Terrain-specific fields if any
            }

            model.addAttribute("immobilier", immobilierForm); // Consistent attribute name
            return "AjoutImmobilier"; // Reuse the same form for editing
        } else {
            return "redirect:/admin/immobiliers"; // Redirect if not found
        }
    }

    /**
     * Handle form submission for updating Immobilier
     */
    @PostMapping("/edit/{id}")
    public String updateImmobilier(@PathVariable Long id, @ModelAttribute("immobilier") ImmobilierForm immobilierForm) {
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
            updatedImmobilier.setType(immobilierForm.getType());

            if (updatedImmobilier instanceof Maison) {
                Maison maison = (Maison) updatedImmobilier;
                maison.setNbChambre(immobilierForm.getNbChambre());
                // Update other Maison-specific fields if any
            } else if (updatedImmobilier instanceof Terrain) {
                // Update Terrain-specific fields if any
            }

            // Handle multiple file uploads
            if (immobilierForm.getPhotos() != null && !immobilierForm.getPhotos().isEmpty()) {
                for (MultipartFile photo : immobilierForm.getPhotos()) {
                    if (!photo.isEmpty()) {
                        try {
                            String filename = System.currentTimeMillis() + "_" + 
                                               Paths.get(photo.getOriginalFilename()).getFileName().toString();
                            Path path = Paths.get(UPLOAD_DIR + filename);
                            Files.createDirectories(path.getParent());
                            Files.write(path, photo.getBytes());
                            updatedImmobilier.getPhotoPaths().add("/uploads/" + filename); // Adjust path as needed
                        } catch (IOException e) {
                            e.printStackTrace();
                            // Optional: Add proper error handling, e.g., redirect with error message
                        }
                    }
                }
            }

            immobilierService.save(updatedImmobilier);
            return "redirect:/admin/immobiliers"; // Redirect to the list page
        } else {
            return "redirect:/admin/immobiliers"; // Redirect if not found
        }
    }

    /**
     * Handle deletion of Immobilier
     */
    @PostMapping("/delete/{id}")
    public String deleteImmobilier(@PathVariable Long id) {
        immobilierService.deleteById(id);
        return "redirect:/admin/immobiliers"; // Redirect to the list page
    }

    // Additional controller methods as needed...
}