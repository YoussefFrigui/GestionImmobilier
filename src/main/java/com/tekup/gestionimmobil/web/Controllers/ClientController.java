package com.tekup.gestionimmobil.web.Controllers;

import com.tekup.gestionimmobil.business.services.ClientService;
import com.tekup.gestionimmobil.dto.ClientForm;
import com.tekup.gestionimmobil.dao.entities.Client;
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

    // Display signup form
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("clientForm", new ClientForm());
        return "signup"; // Ensure this matches your template name
    }

    // Process signup form
    @PostMapping("/signup")
    public String signupClient(@Valid @ModelAttribute("clientForm") ClientForm clientForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Please correct the errors in the form.");
            return "signup";
        }
        clientService.saveClient(clientForm);
        return "redirect:/clients";
    }

    // Display register form
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new ClientForm());
        return "register"; // Ensure this matches your template name
    }

    // Process register form
    @PostMapping("/register")
    public String registerClient(@Valid @ModelAttribute("user") ClientForm clientForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Please correct the errors in the form.");
            return "register";
        }
        clientService.saveClient(clientForm);
        return "redirect:/clients";
    }

    // Display edit form
    @GetMapping("/{cin}/edit")
    public String showEditForm(@PathVariable Long cin, Model model) {
        Client client = clientService.getClientById(cin);
        if (client == null) {
            model.addAttribute("error", "Client not found.");
            return "editClient";
        }
        model.addAttribute("clientForm", new ClientForm(client.getCin(), client.getNom(), client.getPrenom(), client.getEmail(), client.getPassword()));
        return "editClient";
    }

    // Process edit form
    @PostMapping("/{cin}/edit")
    public String editClient(@Valid @ModelAttribute("clientForm") ClientForm clientForm, BindingResult bindingResult, @PathVariable Long cin, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Please correct the errors in the form.");
            return "editClient";
        }
        clientService.updateClient(cin, clientForm);
        return "redirect:/clients";
    }

    // Delete client
    @GetMapping("/{cin}/delete")
    public String deleteClient(@PathVariable Long cin, Model model) {
        clientService.deleteClient(cin);
        return "redirect:/clients";
    }

    // List clients
    @GetMapping
    public String listClients(Model model) {
        List<Client> clients = clientService.getAllClients();
        model.addAttribute("clients", clients);
        return "clientList";
    }
}