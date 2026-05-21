package org.example.projet.Controleur;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.projet.Entites.*;
import org.example.projet.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {


    private IUserService userService;


    private IEntrepriseService entrepriseService;


    private IMissionService missionService;

    private ICompetenceService competenceService;


    private ICandidatureService candidatureService;


    private IProfilService profilService;


    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null || utilisateur.getRole() != RoleType.ADMIN) {
            return "redirect:/login";
        }

        // Statistiques
        long nbFreelances = userService.getUtilisateursByRole(RoleType.FREELANCE).size();
        long nbEntrepreneurs = userService.getUtilisateursByRole(RoleType.ENTREPRENEUR).size();
        long nbEntreprises = entrepriseService.getAllEntreprises().size();
        long nbMissions = missionService.getAllMissionsActives().size();
        long nbCompetences = competenceService.getAllCompetences().size();

        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("nbFreelances", nbFreelances);
        model.addAttribute("nbEntrepreneurs", nbEntrepreneurs);
        model.addAttribute("nbEntreprises", nbEntreprises);
        model.addAttribute("nbMissions", nbMissions);
        model.addAttribute("nbCompetences", nbCompetences);

        return "admin/dashboard";
    }


    @GetMapping("/utilisateurs")
    public String listeUtilisateurs(HttpSession session, Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null || utilisateur.getRole() != RoleType.ADMIN) {
            return "redirect:/login";
        }

        List<Utilisateur> utilisateurs = userService.getAllUtilisateurs();

        model.addAttribute("utilisateurs", utilisateurs);
        model.addAttribute("utilisateur", utilisateur);

        return "admin/utilisateurs";
    }


    @PostMapping("/utilisateur/{id}/supprimer")
    public String supprimerUtilisateur(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUtilisateur(id);
            redirectAttributes.addFlashAttribute("success", "Utilisateur supprimé avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur : " + e.getMessage());
        }
        return "redirect:/admin/utilisateurs";
    }


    @GetMapping("/entreprises")
    public String listeEntreprises(HttpSession session, Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null || utilisateur.getRole() != RoleType.ADMIN) {
            return "redirect:/login";
        }

        List<Entreprise> entreprises = entrepriseService.getAllEntreprises();

        model.addAttribute("entreprises", entreprises);
        model.addAttribute("utilisateur", utilisateur);

        return "admin/entreprises";
    }


    @PostMapping("/entreprise/{id}/supprimer")
    public String supprimerEntreprise(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            entrepriseService.deleteEntreprise(id);
            redirectAttributes.addFlashAttribute("success", "Entreprise supprimée avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur : " + e.getMessage());
        }
        return "redirect:/admin/entreprises";
    }

    @GetMapping("/missions")
    public String listeMissions(HttpSession session, Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null || utilisateur.getRole() != RoleType.ADMIN) {
            return "redirect:/login";
        }

        List<Mission> missions = missionService.getAllMissionsActives();

        model.addAttribute("missions", missions);
        model.addAttribute("utilisateur", utilisateur);

        return "admin/missions";
    }


    @PostMapping("/mission/{id}/desactiver")
    public String desactiverMission(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            missionService.desactiverMission(id);
            redirectAttributes.addFlashAttribute("success", "Mission désactivée");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur : " + e.getMessage());
        }
        return "redirect:/admin/missions";
    }


    @PostMapping("/mission/{id}/supprimer")
    public String supprimerMission(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            missionService.deleteMission(id);
            redirectAttributes.addFlashAttribute("success", "Mission supprimée avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur : " + e.getMessage());
        }
        return "redirect:/admin/missions";
    }


    @GetMapping("/competences")
    public String listeCompetences(HttpSession session, Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null || utilisateur.getRole() != RoleType.ADMIN) {
            return "redirect:/login";
        }

        List<Competence> competences = competenceService.getAllCompetences();

        model.addAttribute("competences", competences);
        model.addAttribute("utilisateur", utilisateur);

        return "admin/competences";
    }


    @PostMapping("/competences/ajouter")
    public String ajouterCompetence(@RequestParam String nom,
                                   @RequestParam(required = false) String description,
                                   RedirectAttributes redirectAttributes) {
        try {
            competenceService.creerCompetence(nom, description);
            redirectAttributes.addFlashAttribute("success", "Compétence ajoutée avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur : " + e.getMessage());
        }
        return "redirect:/admin/competences";
    }


    @PostMapping("/competence/{id}/supprimer")
    public String supprimerCompetence(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            competenceService.deleteCompetence(id);
            redirectAttributes.addFlashAttribute("success", "Compétence supprimée avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur : " + e.getMessage());
        }
        return "redirect:/admin/competences";
    }


    @GetMapping("/freelances")
    public String listeFreelances(HttpSession session, Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null || utilisateur.getRole() != RoleType.ADMIN) {
            return "redirect:/login";
        }

        List<Profil> freelances = profilService.getAllFreelances();

        model.addAttribute("freelances", freelances);
        model.addAttribute("utilisateur", utilisateur);

        return "admin/freelances";
    }


    @GetMapping("/freelance/{id}")
    public String detailFreelance(@PathVariable Long id, HttpSession session, Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null || utilisateur.getRole() != RoleType.ADMIN) {
            return "redirect:/login";
        }

        Profil profil = profilService.getProfilById(id)
                .orElseThrow(() -> new RuntimeException("Profil introuvable"));

        model.addAttribute("profil", profil);
        model.addAttribute("utilisateur", utilisateur);

        return "admin/freelance-detail";
    }
}
