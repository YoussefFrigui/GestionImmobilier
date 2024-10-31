package com.tekup.gestionimmobil.web.Controllers;

import com.tekup.gestionimmobil.web.models.Client;
import com.tekup.gestionimmobil.web.models.ClientForm;
import com.tekup.gestionimmobil.web.models.User;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/Client")
public class ClientController {

//static list of clients empty
private static List<Client> client = new ArrayList<>();

@RequestMapping("/SignUp")
public String SignUp(Model model ) {
    model.addAttribute("clientForm",new ClientForm() );
    return "SignUp";

}
@RequestMapping(path = "/SignUp", method = RequestMethod.POST)
public String SignUp(@Valid @ModelAttribute("clientForm") ClientForm clientForm, BindingResult bindingresult, Model model) {
    if (bindingresult.hasErrors()) {
        model.addAttribute("error", "error");
        return "SignUp";
    }
    client.add(new Client());
    return "redirect:/Client/SignUp";
}
@RequestMapping("/{cin}/edit")
public String editClientForm(@PathVariable Long cin, Model model) {
   // ClientForm clientForm = client.stream().filter(c -> c.getCin().equals(cin)).findFirst().get();
    for (Client client: client) {
        if (client.getCin().equals(cin)) {
            model.addAttribute("clientForm", new ClientForm(client.getCin(), client.getNom(), client.getPrenom(), client.getEmail(), client.getPassword(), client.getRole()));
            model.addAttribute("cin", cin);
            break;
        }
    }
    return "editClientForm";

}
@RequestMapping(path = "/{cin}/edit", method = RequestMethod.POST)
public String editClient(@Valid @ModelAttribute ClientForm clientForm, BindingResult bindingResult, @PathVariable Long cin, Model model) {
    if (bindingResult.hasErrors()) {
        model.addAttribute("error", "error");
        return "editClientForm";
    }
    for (Client client: client) {
        if (client.getCin()==cin) {
            client.setNom(clientForm.getNom());
            client.setPrenom(clientForm.getPrenom());
            client.setEmail(clientForm.getEmail());
            client.setPassword(clientForm.getPassword());
            client.setRole(clientForm.getRole());
            break;
        }
    }
    return "redirect:/Client/SignUp";
}
}