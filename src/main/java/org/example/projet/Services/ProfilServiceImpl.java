package org.example.projet.Services;

import lombok.AllArgsConstructor;
import org.example.projet.Entites.Competence;
import org.example.projet.Entites.Profil;
import org.example.projet.Entites.Utilisateur;
import org.example.projet.Repository.CompetenceRepository;
import org.example.projet.Repository.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class ProfilServiceImpl implements IProfilService {


    private ProfilRepository profilRepository;
    

    private CompetenceRepository competenceRepository;

    // Chemins de stockage des fichiers
    private static final String UPLOAD_DIR = "uploads/";
    private static final String PHOTO_DIR = UPLOAD_DIR + "photos/";
    private static final String CV_DIR = UPLOAD_DIR + "cv/";

    @Override
    public Profil creerProfil(Utilisateur utilisateur, String nom, String prenom, String telephone, String biographie) {
        Profil profil = new Profil();
        profil.setUtilisateur(utilisateur);
        profil.setNom(nom);
        profil.setPrenom(prenom);
        profil.setTelephone(telephone);
        profil.setBiographie(biographie);
        
        return profilRepository.save(profil);
    }

    @Override
    public Optional<Profil> getProfilById(Long id) {
        return profilRepository.findById(id);
    }

    @Override
    public Optional<Profil> getProfilByUtilisateur(Utilisateur utilisateur) {
        return profilRepository.findByUtilisateur(utilisateur);
    }

    @Override
    public Profil updateProfil(Profil profil) {
        return profilRepository.save(profil);
    }

    @Override
    public Profil ajouterCompetences(Long profilId, List<Long> competenceIds) {
        Profil profil = profilRepository.findById(profilId)
                .orElseThrow(() -> new IllegalArgumentException("Profil introuvable"));
        
        List<Competence> competences = competenceRepository.findAllById(competenceIds);
        profil.setCompetences(competences);
        
        return profilRepository.save(profil);
    }

    @Override
    public String uploadPhotoProfil(Long profilId, MultipartFile file) throws Exception {
        Profil profil = profilRepository.findById(profilId)
                .orElseThrow(() -> new IllegalArgumentException("Profil introuvable"));

        // Création du dossier si nécessaire
        File uploadDir = new File(PHOTO_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Génération d'un nom de fichier unique
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = "photo_" + profilId + "_" + UUID.randomUUID().toString() + extension;

        // Sauvegarde du fichier
        Path filepath = Paths.get(PHOTO_DIR, newFilename);
        Files.copy(file.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);

        // Mise à jour du profil
        profil.setPhotoProfil(newFilename);
        profilRepository.save(profil);

        return newFilename;
    }

    @Override
    public String uploadCvPdf(Long profilId, MultipartFile file) throws Exception {
        Profil profil = profilRepository.findById(profilId)
                .orElseThrow(() -> new IllegalArgumentException("Profil introuvable"));

        // Vérification du type de fichier
        if (!file.getContentType().equals("application/pdf")) {
            throw new IllegalArgumentException("Le fichier doit être un PDF");
        }

        // Création du dossier si nécessaire
        File uploadDir = new File(CV_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Génération d'un nom de fichier unique
        String newFilename = "cv_" + profilId + "_" + UUID.randomUUID().toString() + ".pdf";

        // Sauvegarde du fichier
        Path filepath = Paths.get(CV_DIR, newFilename);
        Files.copy(file.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);

        // Mise à jour du profil
        profil.setCvPdf(newFilename);
        profilRepository.save(profil);

        return newFilename;
    }

    @Override
    public List<Profil> getAllFreelances() {
        return profilRepository.findAllFreelances();
    }

    @Override
    public List<Profil> getProfilsByCompetences(List<Competence> competences) {
        return profilRepository.findByCompetencesIn(competences);
    }

    @Override
    public void deleteProfil(Long id) {
        profilRepository.deleteById(id);
    }
}
