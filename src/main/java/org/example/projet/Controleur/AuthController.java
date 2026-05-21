package org.example.projet.Controleur;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.projet.Entites.Profil;
import org.example.projet.Entites.RoleType;
import org.example.projet.Entites.Utilisateur;
import org.example.projet.Services.IProfilService;
import org.example.projet.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@AllArgsConstructor
@Controller
public class AuthController {


    private IUserService userService;


    private IProfilService profilService;


    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String email, 
                       @RequestParam String password, 
                       HttpSession session, 
                       RedirectAttributes redirectAttributes) {
        
        Optional<Utilisateur> utilisateur = userService.authentifier(email, password);

        if (utilisateur.isPresent()) {
            Utilisateur user = utilisateur.get();
            session.setAttribute("utilisateurConnecte", user);

            // Redirection selon le rôle
            if (user.getRole() == RoleType.ADMIN) {
                return "redirect:/admin/dashboard";
            } else if (user.getRole() == RoleType.ENTREPRENEUR) {
                return "redirect:/entrepreneur/dashboard";
            } else {
                return "redirect:/freelance/dashboard";
            }
        }

        redirectAttributes.addFlashAttribute("error", "Email ou mot de passe incorrect");
        return "redirect:/login";
    }


    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }


    @PostMapping("/register")
    public String register(@RequestParam String email,
                          @RequestParam String password,
                          @RequestParam String confirmPassword,
                          @RequestParam RoleType role,
                          @RequestParam String nom,
                          @RequestParam String prenom,
                          @RequestParam(required = false) String telephone,
                          @RequestParam(required = false) String biographie,
                          RedirectAttributes redirectAttributes) {

        // Validation
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Les mots de passe ne correspondent pas");
            return "redirect:/register";
        }

        if (userService.emailExists(email)) {
            redirectAttributes.addFlashAttribute("error", "Cet email est déjà utilisé");
            return "redirect:/register";
        }

        try {
            // Créer l'utilisateur
            Utilisateur utilisateur = userService.creerUtilisateur(email, password, role);

            // Créer le profil (COMPOSITION - automatique)
            profilService.creerProfil(utilisateur, nom, prenom, telephone, biographie);

            redirectAttributes.addFlashAttribute("success", "Inscription réussie ! Vous pouvez maintenant vous connecter.");
            return "redirect:/login";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'inscription : " + e.getMessage());
            return "redirect:/register";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }


    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }
}
