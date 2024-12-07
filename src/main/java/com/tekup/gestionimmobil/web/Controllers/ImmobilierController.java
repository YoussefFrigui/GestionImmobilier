package com.tekup.gestionimmobil.web.Controllers;

import com.tekup.gestionimmobil.business.services.ImmobilierService;
import com.tekup.gestionimmobil.dao.entities.Immobilier;
import com.tekup.gestionimmobil.dto.ImmobilierForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/immobiliers")
public class ImmobilierController {
    @Autowired
    private ImmobilierService immobilierService;

    private static String UPLOAD_DIR = "uploads/";

    @GetMapping
    public String getAllImmobiliers(Model model) {
        List<Immobilier> immobiliers = immobilierService.findAll();
        model.addAttribute("immobiliers", immobiliers);
        return "immobilierList";
    }

    @GetMapping("/new")
    public String showAddImmobilierForm(Model model) {
        model.addAttribute("immobilierForm", new ImmobilierForm());
        return "immobilierForm";
    }

    @PostMapping
    public String createImmobilier(@ModelAttribute ImmobilierForm immobilierForm) {
        Immobilier immobilier = new Immobilier();
        immobilier.setPrix(immobilierForm.getPrix());
        immobilier.setVille(immobilierForm.getVille());
        immobilier.setDelegation(immobilierForm.getDelegation());
        immobilier.setNbPieces(immobilierForm.getNbPieces());
        immobilier.setDescription(immobilierForm.getDescription());
        immobilier.setSurface(immobilierForm.getSurface());
        immobilier.setTelContact(immobilierForm.getTelContact());
        immobilier.setType(immobilierForm.getType());
        immobilier.setEtat(immobilierForm.getEtat());

        // Handle file upload
        MultipartFile photo = immobilierForm.getPhoto();
        if (photo != null && !photo.isEmpty()) {
            try {
                byte[] bytes = photo.getBytes();
                Path path = Paths.get(UPLOAD_DIR + photo.getOriginalFilename());
                Files.write(path, bytes);
                immobilier.setPhoto(photo.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        immobilierService.save(immobilier);
        return "redirect:/admin/immobiliers";
    }

    @GetMapping("/edit/{id}")
    public String showEditImmobilierForm(@PathVariable Long id, Model model) {
        Optional<Immobilier> immobilier = immobilierService.findById(id);
        if (immobilier.isPresent()) {
            ImmobilierForm immobilierForm = new ImmobilierForm();
            Immobilier existingImmobilier = immobilier.get();
            immobilierForm.setId(existingImmobilier.getId());
            immobilierForm.setPrix(existingImmobilier.getPrix());
            immobilierForm.setVille(existingImmobilier.getVille());
            immobilierForm.setDelegation(existingImmobilier.getDelegation());
            immobilierForm.setNbPieces(existingImmobilier.getNbPieces());
            immobilierForm.setDescription(existingImmobilier.getDescription());
            immobilierForm.setSurface(existingImmobilier.getSurface());
            immobilierForm.setTelContact(existingImmobilier.getTelContact());
            immobilierForm.setType(existingImmobilier.getType());
            immobilierForm.setEtat(existingImmobilier.getEtat());
            model.addAttribute("immobilierForm", immobilierForm);
            return "immobilierForm";
        } else {
            return "redirect:/admin/immobiliers";
        }
    }

    @PostMapping("/{id}")
    public String updateImmobilier(@PathVariable Long id, @ModelAttribute ImmobilierForm immobilierForm) {
        Optional<Immobilier> immobilier = immobilierService.findById(id);
        if (immobilier.isPresent()) {
            Immobilier updatedImmobilier = immobilier.get();
            updatedImmobilier.setPrix(immobilierForm.getPrix());
            updatedImmobilier.setVille(immobilierForm.getVille());
            updatedImmobilier.setDelegation(immobilierForm.getDelegation());
            updatedImmobilier.setNbPieces(immobilierForm.getNbPieces());
            updatedImmobilier.setDescription(immobilierForm.getDescription());
            updatedImmobilier.setSurface(immobilierForm.getSurface());
            updatedImmobilier.setTelContact(immobilierForm.getTelContact());
            updatedImmobilier.setType(immobilierForm.getType());
            updatedImmobilier.setEtat(immobilierForm.getEtat());

            // Handle file upload
            MultipartFile photo = immobilierForm.getPhoto();
            if (photo != null && !photo.isEmpty()) {
                try {
                    byte[] bytes = photo.getBytes();
                    Path path = Paths.get(UPLOAD_DIR + photo.getOriginalFilename());
                    Files.write(path, bytes);
                    updatedImmobilier.setPhoto(photo.getOriginalFilename());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            immobilierService.save(updatedImmobilier);
            return "redirect:/admin/immobiliers";
        } else {
            return "redirect:/admin/immobiliers";
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImmobilier(@PathVariable Long id) {
        immobilierService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}