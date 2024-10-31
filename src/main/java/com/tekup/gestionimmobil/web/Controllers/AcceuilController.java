package com.tekup.gestionimmobil.web.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AcceuilController {

    @GetMapping("/acceuil")
    public String showAcceuilPage() {
        return "acceuil"; // This should match the name of your acceuil.html file
    }
    @GetMapping("/signin")
    public String showSignInPage() {
        return "signin"; // This should match the name of your signin.html file
    }   
}