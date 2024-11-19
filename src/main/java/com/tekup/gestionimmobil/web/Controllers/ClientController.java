package com.tekup.gestionimmobil.web.Controllers;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tekup.gestionimmobil.dao.entities.Client;
import com.tekup.gestionimmobil.dao.entities.ClientForm;

@Controller

@RequestMapping("/Client")
public class ClientController {

    // Static list of clients
    private static List<Client> clients = new ArrayList<>();

    @RequestMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("clientForm", new ClientForm());
        return "SignUp";
    }

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public String signUp(@Valid @ModelAttribute("clientForm") ClientForm clientForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Please correct the errors in the form.");
            return "SignUp";
        }
        Client client = new Client();
        client.setCin(clientForm.getCin());
        client.setNom(clientForm.getNom());
        client.setPrenom(clientForm.getPrenom());
        client.setEmail(clientForm.getEmail());
        client.setPassword(clientForm.getPassword());
        client.setRole(clientForm.getRole());
        clients.add(client);
        return "redirect:/Client/signup";
    }

    @RequestMapping("/{cin}/edit")
    public String showEditClientForm(@PathVariable Long cin, Model model) {
        for (Client client : clients) {
            if (client.getCin().equals(cin)) {
                model.addAttribute("clientForm", new ClientForm(client.getCin(), client.getNom(), client.getPrenom(), client.getEmail(), client.getPassword(), client.getRole()));
                model.addAttribute("cin", cin);
                return "editClientForm";
            }
        }
        model.addAttribute("error", "Client not found.");
        return "redirect:/Client/signup";
    }

    @RequestMapping(path = "/{cin}/edit", method = RequestMethod.POST)
    public String editClient(@Valid @ModelAttribute("clientForm") ClientForm clientForm, BindingResult bindingResult, @PathVariable Long cin, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Please correct the errors in the form.");
            return "editClientForm";
        }
        for (Client client : clients) {
            if (client.getCin().equals(cin)) {
                client.setNom(clientForm.getNom());
                client.setPrenom(clientForm.getPrenom());
                client.setEmail(clientForm.getEmail());
                client.setPassword(clientForm.getPassword());
                client.setRole(clientForm.getRole());
                return "redirect:/Client/signup";
            }
        }
        model.addAttribute("error", "Client not found.");
        return "editClientForm";
    }
}