package com.tekup.gestionimmobil.web.controllers;

import com.tekup.gestionimmobil.business.services.UserService;
import com.tekup.gestionimmobil.dao.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, Model model) {
        userService.createUser(username, password, "ADMIN");
        model.addAttribute("message", "User registered successfully!");
        return "login";
    }
}