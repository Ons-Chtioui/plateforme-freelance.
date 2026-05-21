package org.example.projet.Controleur;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.projet.Entites.Candidature;
import org.example.projet.Entites.Mission;
import org.example.projet.Entites.Profil;
import org.example.projet.Entites.Utilisateur;
import org.example.projet.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Contrôleur pour les fonctionnalités Freelance
 * - Dashboard
 * - Matching missions selon compétences
 * - Postulation aux missions
 * - Suivi des candidatures
 * - Gestion du profil
 */
@Controller
@AllArgsConstructor
@RequestMapping("/freelance")
public class FreelanceController {


    private IProfilService profilService;


    private IMissionService missionService;


    private ICandidatureService candidatureService;


    private ICompetenceService competenceService;


    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null) {
            return "redirect:/login";
        }

        Profil profil = profilService.getProfilByUtilisateur(utilisateur)
                .orElseThrow(() -> new RuntimeException("Profil introuvable"));

        model.addAttribute("profil", profil);
        model.addAttribute("utilisateur", utilisateur);

        return "freelance/dashboard";
    }


    @GetMapping("/missions")
    public String voirMissions(HttpSession session, Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null) {
            return "redirect:/login";
        }

        Profil profil = profilService.getProfilByUtilisateur(utilisateur)
                .orElseThrow(() -> new RuntimeException("Profil introuvable"));

        // Matching : récupérer les missions correspondant aux compétences
        List<Mission> missionsMatchees = missionService.getMissionsMatchingProfil(profil.getId());

        model.addAttribute("missions", missionsMatchees);
        model.addAttribute("profil", profil);
        model.addAttribute("candidatureService", candidatureService);

        return "freelance/missions";
    }


    @GetMapping("/mission/{id}")
    public String detailMission(@PathVariable Long id, HttpSession session, Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null) {
            return "redirect:/login";
        }

        Profil profil = profilService.getProfilByUtilisateur(utilisateur)
                .orElseThrow(() -> new RuntimeException("Profil introuvable"));

        Mission mission = missionService.getMissionById(id)
                .orElseThrow(() -> new RuntimeException("Mission introuvable"));

        boolean dejaPostule = candidatureService.aDejaPostule(mission.getId(), profil.getId());

        model.addAttribute("mission", mission);
        model.addAttribute("profil", profil);
        model.addAttribute("dejaPostule", dejaPostule);

        return "freelance/mission-detail";
    }


    @PostMapping("/postuler/{missionId}")
    public String postuler(@PathVariable Long missionId,
                          @RequestParam(required = false) String messageMotivation,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null) {
            return "redirect:/login";
        }

        Profil profil = profilService.getProfilByUtilisateur(utilisateur)
                .orElseThrow(() -> new RuntimeException("Profil introuvable"));

        try {
            candidatureService.postuler(missionId, profil.getId(), messageMotivation);
            redirectAttributes.addFlashAttribute("success", "Candidature envoyée avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/freelance/missions";
    }


    @GetMapping("/mes-candidatures")
    public String mesCandidatures(HttpSession session, Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null) {
            return "redirect:/login";
        }

        Profil profil = profilService.getProfilByUtilisateur(utilisateur)
                .orElseThrow(() -> new RuntimeException("Profil introuvable"));

        List<Candidature> candidatures = candidatureService.getCandidaturesFreelance(profil.getId());

        model.addAttribute("candidatures", candidatures);
        model.addAttribute("profil", profil);

        return "freelance/mes-candidatures";
    }


    @GetMapping("/profil")
    public String afficherProfil(HttpSession session, Model model) {
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("utilisateurConnecte");
        if (utilisateur == null) {
            return "redirect:/login";
        }

        Profil profil = profilService.getProfilByUtilisateur(utilisateur)
                .orElseThrow(() -> new RuntimeException("Profil introuvable"));

        model.addAttribute("profil", profil);
        model.addAttribute("competences", competenceService.getAllCompetences());

        return "freelance/profil";
    }


    @PostMapping("/profil/update")
    public String updateProfil(@RequestParam Long profilId,
                              @RequestParam String nom,
                              @RequestParam String prenom,
                              @RequestParam String telephone,
                              @RequestParam String biographie,
                              @RequestParam(required = false) List<Long> competenceIds,
                              RedirectAttributes redirectAttributes) {
        try {
            Profil profil = profilService.getProfilById(profilId)
                    .orElseThrow(() -> new RuntimeException("Profil introuvable"));

            profil.setNom(nom);
            profil.setPrenom(prenom);
            profil.setTelephone(telephone);
            profil.setBiographie(biographie);

            profilService.updateProfil(profil);

            // Mise à jour des compétences
            if (competenceIds != null && !competenceIds.isEmpty()) {
                profilService.ajouterCompetences(profilId, competenceIds);
            }

            redirectAttributes.addFlashAttribute("success", "Profil mis à jour avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/freelance/profil";
    }


    @PostMapping("/profil/upload-photo")
    public String uploadPhoto(@RequestParam Long profilId,
                             @RequestParam("photo") MultipartFile photo,
                             RedirectAttributes redirectAttributes) {
        try {
            if (photo.isEmpty()) {
                throw new IllegalArgumentException("Veuillez sélectionner une photo");
            }

            profilService.uploadPhotoProfil(profilId, photo);
            redirectAttributes.addFlashAttribute("success", "Photo de profil mise à jour");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'upload : " + e.getMessage());
        }

        return "redirect:/freelance/profil";
    }


    @PostMapping("/profil/upload-cv")
    public String uploadCv(@RequestParam Long profilId,
                          @RequestParam("cv") MultipartFile cv,
                          RedirectAttributes redirectAttributes) {
        try {
            if (cv.isEmpty()) {
                throw new IllegalArgumentException("Veuillez sélectionner un fichier PDF");
            }

            profilService.uploadCvPdf(profilId, cv);
            redirectAttributes.addFlashAttribute("success", "CV mis à jour avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de l'upload : " + e.getMessage());
        }

        return "redirect:/freelance/profil";
    }
}
