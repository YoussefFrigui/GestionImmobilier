package com.tekup.gestionimmobil.web.Controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/register")
    public String registerPage() {
        return "This is the register page.";
    }




    
    // Soumettre le formulaire d'inscription
    @PostMapping("/register")
    public String processRegister(@ModelAttribute UserController user) {
        // Simulez la logique de sauvegarde (vous pouvez ajouter une base de données ici)
        System.out.println("User enregistré : " + user.getUsername());
                return "register_success"; // Redirige vers une page de succès
            }
        
        
        
        
        
            private String getUsername() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
            }
}
