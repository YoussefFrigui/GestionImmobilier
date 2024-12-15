package com.tekup.gestionimmobil.web.controllers;

import com.tekup.gestionimmobil.business.services.ImmobilierService;
import com.tekup.gestionimmobil.dao.entities.Immobilier;
import com.tekup.gestionimmobil.dao.entities.Maison;
import com.tekup.gestionimmobil.dao.entities.Terrain;
import com.tekup.gestionimmobil.web.models.ImmobilierForm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin/immobiliers")
public class ImmobilierController {

    @Autowired
    private ImmobilierService immobilierService;

    private static final String UPLOAD_DIR = "uploads/";
    private static final long MAX_FILE_SIZE = 5_242_880L; // 5MB

    public ImmobilierController() {
        createUploadDirectory();
    }

    private void createUploadDirectory() {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    @GetMapping
    public String getAllImmobiliers(Model model) {
        model.addAttribute("immobiliers", immobilierService.findAll());
        model.addAttribute("isAdmin", true);
        return "immobilierList";
    }

    @GetMapping("/add")
public String showAddImmobilierForm(Model model) {
    if (!model.containsAttribute("immobilierForm")) {
        ImmobilierForm form = new ImmobilierForm();
        form.setEtat(Immobilier.Etat.A_VENDRE);
        model.addAttribute("immobilierForm", form);
    }
    return "AjoutImmobilier";
}

    @PostMapping("/add")
    public String createImmobilier(
            @Valid @ModelAttribute("immobilierForm") ImmobilierForm form,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {
        
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Please correct the form errors");
            return "AjoutImmobilier";
        }

        try {
            validatePhotos(form.getPhotos());
            Immobilier immobilier = createImmobilierFromForm(form);
            handleFileUploads(form.getPhotos(), immobilier);
            immobilierService.save(immobilier);
            redirectAttributes.addFlashAttribute("successMessage", "Property added successfully");
            return "redirect:/admin/immobiliers";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "AjoutImmobilier";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditImmobilierForm(@PathVariable Long id, Model model) {
        Optional<Immobilier> immobilier = immobilierService.findById(id);
        if (immobilier.isPresent()) {
            model.addAttribute("immobilierForm", convertToForm(immobilier.get()));
            return "AjoutImmobilier";
        }
        return "redirect:/admin/immobiliers";
    }

    @PostMapping("/edit/{id}")
    public String updateImmobilier(@PathVariable Long id,
                                 @Valid @ModelAttribute("immobilierForm") ImmobilierForm form,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Please correct the form errors");
            return "AjoutImmobilier";
        }

        try {
            Optional<Immobilier> optionalImmobilier = immobilierService.findById(id);
            if (!optionalImmobilier.isPresent()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Immobilier not found");
                return "redirect:/admin/immobiliers";
            }

            Immobilier existingImmobilier = optionalImmobilier.get();
            validatePhotos(form.getPhotos());
            updateImmobilierFromForm(existingImmobilier, form);
            handleFileUploads(form.getPhotos(), existingImmobilier);
            immobilierService.save(existingImmobilier);
            
            redirectAttributes.addFlashAttribute("successMessage", "Property updated successfully");
            return "redirect:/admin/immobiliers";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "AjoutImmobilier";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteImmobilier(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            immobilierService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Property deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting property: " + e.getMessage());
        }
        return "redirect:/admin/immobiliers";
    }

    private void validatePhotos(List<MultipartFile> photos) throws Exception {
        if (photos != null) {
            for (MultipartFile photo : photos) {
                if (!photo.isEmpty()) {
                    String contentType = photo.getContentType();
                    if (contentType == null || !contentType.startsWith("image/")) {
                        throw new IllegalArgumentException("Invalid file type. Only images are allowed.");
                    }
                    if (photo.getSize() > MAX_FILE_SIZE) {
                        throw new IllegalArgumentException("File size too large. Maximum size is 5MB.");
                    }
                }
            }
        }
    }

    private Immobilier createImmobilierFromForm(ImmobilierForm form) {
        Immobilier immobilier;
        if ("Maison".equals(form.getType())) {
            Maison maison = new Maison();
            maison.setNbChambre(form.getNbChambre());
            immobilier = maison;
        } else {
            immobilier = new Terrain();
        }

        updateImmobilierFromForm(immobilier, form);
        return immobilier;
    }

    private void updateImmobilierFromForm(Immobilier immobilier, ImmobilierForm form) {
        immobilier.setDescription(form.getDescription());
        immobilier.setPrix(form.getPrix());
        immobilier.setVille(form.getVille());
        immobilier.setDelegation(form.getDelegation());
        immobilier.setSurface(form.getSurface());
        immobilier.setContact(form.getContact());
        immobilier.setEtat(form.getEtat());
        immobilier.setType(form.getType());

        if (immobilier instanceof Maison && "Maison".equals(form.getType())) {
            ((Maison) immobilier).setNbChambre(form.getNbChambre());
        }
    }

    private void handleFileUploads(List<MultipartFile> photos, Immobilier immobilier) throws IOException {
        if (photos == null || photos.isEmpty()) {
            return;
        }

        List<String> photoPaths = immobilier.getPhotoPaths();
        if (photoPaths == null) {
            photoPaths = new ArrayList<>();
            immobilier.setPhotoPaths(photoPaths);
        }

        for (MultipartFile photo : photos) {
            if (!photo.isEmpty()) {
                String fileName = generateUniqueFileName(photo.getOriginalFilename());
                Path filePath = Paths.get(UPLOAD_DIR, fileName);
                Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                photoPaths.add("/uploads/" + fileName);
            }
        }
    }

    private ImmobilierForm convertToForm(Immobilier immobilier) {
        ImmobilierForm form = new ImmobilierForm();
        form.setId(immobilier.getId());
        form.setDescription(immobilier.getDescription());
        form.setPrix(immobilier.getPrix());
        form.setVille(immobilier.getVille());
        form.setDelegation(immobilier.getDelegation());
        form.setSurface(immobilier.getSurface());
        form.setContact(immobilier.getContact());
        form.setEtat(immobilier.getEtat());
        form.setType(immobilier.getType());

        if (immobilier instanceof Maison) {
            form.setNbChambre(((Maison) immobilier).getNbChambre());
        }

        return form;
    }

    private String generateUniqueFileName(String originalFilename) {
        String extension = StringUtils.getFilenameExtension(originalFilename);
        return UUID.randomUUID().toString() + "." + extension;
    }
}