package com.tekup.gestionimmobil.web.Controllers;

import com.tekup.gestionimmobil.dao.entities.Client;
import com.tekup.gestionimmobil.dto.ClientForm;
import com.tekup.gestionimmobil.business.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // Afficher le formulaire d'inscription
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("clientForm", new ClientForm());
        return "signup"; // Assurez-vous que le fichier signup.html existe dans src/main/resources/templates
    }

    // Traiter le formulaire d'inscription
    @PostMapping("/signup")
    public String signupClient(@Valid @ModelAttribute("clientForm") ClientForm clientForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Veuillez corriger les erreurs dans le formulaire.");
            return "signup";
        }
        clientService.saveClient(clientForm);
        return "redirect:/clients";
    }

    // Afficher le formulaire de modification
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Client client = clientService.getClientById(id);
        if (client == null) {
            model.addAttribute("error", "Client introuvable.");
            return "editClientForm"; // Assurez-vous que ce fichier existe
        }
        model.addAttribute("clientForm", new ClientForm(client.getId(), client.getUsername(), client.getEmail(), client.getPassword()));
        return "editClientForm";
    }

    // Traiter la modification du client
    @PostMapping("/{id}/edit")
    public String editClient(@Valid @ModelAttribute("clientForm") ClientForm clientForm, BindingResult bindingResult, @PathVariable Long id, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Veuillez corriger les erreurs dans le formulaire.");
            return "editClientForm";
        }
        clientService.updateClient(id, clientForm);
        return "redirect:/clients";
    }

    // Supprimer un client
    @GetMapping("/{id}/delete")
    public String deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return "redirect:/clients";
    }

    // Liste des clients
    @GetMapping
    public String listClients(Model model) {
        List<Client> clients = clientService.getAllClients();
        model.addAttribute("clients", clients);
        return "clientList"; // Assurez-vous que le fichier clientList.html existe
    }
}
