package org.example.projet.Services;

import org.example.projet.Entites.Competence;
import org.example.projet.Entites.Profil;
import org.example.projet.Entites.Utilisateur;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IProfilService {
    

    Profil creerProfil(Utilisateur utilisateur, String nom, String prenom, String telephone, String biographie);
    

    Optional<Profil> getProfilById(Long id);
    

    Optional<Profil> getProfilByUtilisateur(Utilisateur utilisateur);
    

    Profil updateProfil(Profil profil);
    

    Profil ajouterCompetences(Long profilId, List<Long> competenceIds);
    

    String uploadPhotoProfil(Long profilId, MultipartFile file) throws Exception;
    

    String uploadCvPdf(Long profilId, MultipartFile file) throws Exception;
    

    List<Profil> getAllFreelances();
    

    List<Profil> getProfilsByCompetences(List<Competence> competences);
    

    void deleteProfil(Long id);
}
