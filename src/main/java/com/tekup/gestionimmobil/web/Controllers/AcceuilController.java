package com.tekup.gestionimmobil.web.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AcceuilController {

    @GetMapping("/acceuil")
    public String showAcceuilPage() {
        return "Acceuil"; // Matches Acceuil.html
    }

    @GetMapping("/signin")
    public String showSignInPage() {
        return "signin"; // Matches signin.html
    }
}