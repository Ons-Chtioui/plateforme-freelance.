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

/**
 * Contrôleur pour les fonctionnalités Entrepreneur
 * - Dashboard
 * - Gestion de l'entreprise (création obligatoire)
 * - Publication de missions
 * - Gestion des candidatures (Accepter/Refuser)
 */
@Controller
@AllArgsConstructor
@RequestMapping("/entrepreneur")
public class EntrepreneurController {


    private IEntrepriseService entrepriseService;


    private IMissionService missionService;


    private ICandidatureService candidatureService;


    private ICompetenceService competenceService;


    private IProfilService profilService;


    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null) {
            return "redirect:/login";
        }

        // Vérifier si l'entrepreneur a créé son entreprise
        boolean hasEntreprise = entrepriseService.entrepreneurHasEntreprise(utilisateur.getId());

        if (!hasEntreprise) {
            return "redirect:/entrepreneur/creer-entreprise";
        }

        Entreprise entreprise = entrepriseService.getEntrepriseByEntrepreneur(utilisateur.getId())
                .orElseThrow(() -> new RuntimeException("Entreprise introuvable"));

        List<Mission> missions = missionService.getMissionsByEntrepreneur(utilisateur.getId());

        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("entreprise", entreprise);
        model.addAttribute("missions", missions);

        return "entrepreneur/dashboard";
    }


    @GetMapping("/creer-entreprise")
    public String afficherFormulaireEntreprise(HttpSession session, Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null) {
            return "redirect:/login";
        }

        // Si l'entreprise existe déjà, rediriger vers le dashboard
        if (entrepriseService.entrepreneurHasEntreprise(utilisateur.getId())) {
            return "redirect:/entrepreneur/dashboard";
        }

        model.addAttribute("utilisateur", utilisateur);
        return "entrepreneur/creer-entreprise";
    }


    @PostMapping("/creer-entreprise")
    public String creerEntreprise(@RequestParam String nom,
                                 @RequestParam String secteurActivite,
                                 @RequestParam(required = false) String siteWeb,
                                 @RequestParam(required = false) String description,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null) {
            return "redirect:/login";
        }

        try {
            entrepriseService.creerEntreprise(utilisateur.getId(), nom, secteurActivite, siteWeb, description);
            redirectAttributes.addFlashAttribute("success", "Entreprise créée avec succès !");
            return "redirect:/entrepreneur/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/entrepreneur/creer-entreprise";
        }
    }


    @GetMapping("/missions/ajouter")
    public String afficherFormulaireAjoutMission(HttpSession session, Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null) {
            return "redirect:/login";
        }

        // Vérifier que l'entreprise existe
        if (!entrepriseService.entrepreneurHasEntreprise(utilisateur.getId())) {
            return "redirect:/entrepreneur/creer-entreprise";
        }

        // Récupérer TOUTES les compétences pour sélection multiple
        List<Competence> competences = competenceService.getAllCompetences();

        model.addAttribute("competences", competences);
        model.addAttribute("utilisateur", utilisateur);

        return "entrepreneur/ajouter-mission";
    }


    @PostMapping("/missions/ajouter")
    public String ajouterMission(@RequestParam String titre,
                                @RequestParam String description,
                                @RequestParam double budget,
                                @RequestParam(required = false) List<Long> competenceIds,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null) {
            return "redirect:/login";
        }

        try {
            // L'entreprise est automatiquement récupérée via l'ID de l'entrepreneur
            missionService.creerMission(utilisateur.getId(), titre, description, budget, competenceIds);
            redirectAttributes.addFlashAttribute("success", "Mission publiée avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/entrepreneur/dashboard";
    }


    @GetMapping("/missions")
    public String mesMissions(HttpSession session, Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null) {
            return "redirect:/login";
        }

        List<Mission> missions = missionService.getMissionsByEntrepreneur(utilisateur.getId());

        model.addAttribute("missions", missions);
        model.addAttribute("utilisateur", utilisateur);

        return "entrepreneur/mes-missions";
    }


    @GetMapping("/mission/{id}/candidatures")
    public String voirCandidatures(@PathVariable Long id, HttpSession session, Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null) {
            return "redirect:/login";
        }

        Mission mission = missionService.getMissionById(id)
                .orElseThrow(() -> new RuntimeException("Mission introuvable"));

        List<Candidature> candidatures = candidatureService.getCandidaturesMission(id);

        model.addAttribute("mission", mission);
        model.addAttribute("candidatures", candidatures);
        model.addAttribute("utilisateur", utilisateur);

        return "entrepreneur/candidatures";
    }

    @PostMapping("/candidature/{id}/accepter")
    public String accepterCandidature(@PathVariable Long id,
                                     RedirectAttributes redirectAttributes) {
        try {
            Candidature candidature = candidatureService.accepterCandidature(id);
            redirectAttributes.addFlashAttribute("success", "Candidature acceptée !");
            return "redirect:/entrepreneur/mission/" + candidature.getMission().getId() + "/candidatures";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/entrepreneur/dashboard";
        }
    }


    @PostMapping("/candidature/{id}/refuser")
    public String refuserCandidature(@PathVariable Long id,
                                    RedirectAttributes redirectAttributes) {
        try {
            Candidature candidature = candidatureService.refuserCandidature(id);
            redirectAttributes.addFlashAttribute("success", "Candidature refusée.");
            return "redirect:/entrepreneur/mission/" + candidature.getMission().getId() + "/candidatures";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/entrepreneur/dashboard";
        }
    }


    @GetMapping("/candidatures")
    public String toutesLesCandidatures(HttpSession session, Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null) {
            return "redirect:/login";
        }

        List<Candidature> candidatures = candidatureService.getCandidaturesEntrepreneur(utilisateur.getId());

        model.addAttribute("candidatures", candidatures);
        model.addAttribute("utilisateur", utilisateur);

        return "entrepreneur/toutes-candidatures";
    }


    @GetMapping("/entreprise/modifier")
    public String afficherFormulaireModificationEntreprise(HttpSession session, Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null) {
            return "redirect:/login";
        }

        Entreprise entreprise = entrepriseService.getEntrepriseByEntrepreneur(utilisateur.getId())
                .orElseThrow(() -> new RuntimeException("Entreprise introuvable"));

        model.addAttribute("entreprise", entreprise);
        model.addAttribute("utilisateur", utilisateur);

        return "entrepreneur/modifier-entreprise";
    }


    @PostMapping("/entreprise/modifier")
    public String modifierEntreprise(@RequestParam Long entrepriseId,
                                    @RequestParam String nom,
                                    @RequestParam String secteurActivite,
                                    @RequestParam(required = false) String siteWeb,
                                    @RequestParam(required = false) String description,
                                    RedirectAttributes redirectAttributes) {
        try {
            Entreprise entreprise = entrepriseService.getEntrepriseById(entrepriseId)
                    .orElseThrow(() -> new RuntimeException("Entreprise introuvable"));

            entreprise.setNom(nom);
            entreprise.setSecteurActivite(secteurActivite);
            entreprise.setSiteWeb(siteWeb);
            entreprise.setDescription(description);

            entrepriseService.updateEntreprise(entreprise);
            redirectAttributes.addFlashAttribute("success", "Entreprise mise à jour avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/entrepreneur/dashboard";
    }
}
