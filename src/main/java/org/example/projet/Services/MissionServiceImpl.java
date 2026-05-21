package org.example.projet.Services;

import lombok.AllArgsConstructor;
import org.example.projet.Entites.Competence;
import org.example.projet.Entites.Entreprise;
import org.example.projet.Entites.Mission;
import org.example.projet.Entites.Profil;
import org.example.projet.Repository.CompetenceRepository;
import org.example.projet.Repository.EntrepriseRepository;
import org.example.projet.Repository.MissionRepository;
import org.example.projet.Repository.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class MissionServiceImpl implements IMissionService {


    private MissionRepository missionRepository;
    

    private EntrepriseRepository entrepriseRepository;
    

    private CompetenceRepository competenceRepository;
    

    private ProfilRepository profilRepository;

    @Override
    public Mission creerMission(Long entrepreneurId, String titre, String description, double budget, List<Long> competenceIds) {
        // Récupérer l'entreprise de l'entrepreneur
        Entreprise entreprise = entrepriseRepository.findByEntrepreneurId(entrepreneurId)
                .orElseThrow(() -> new IllegalArgumentException("Vous devez d'abord créer votre entreprise"));

        Mission mission = new Mission();
        mission.setTitre(titre);
        mission.setDescription(description);
        mission.setBudget(budget);
        mission.setEntreprise(entreprise);
        mission.setActive(true);

        // Ajouter les compétences
        if (competenceIds != null && !competenceIds.isEmpty()) {
            List<Competence> competences = competenceRepository.findAllById(competenceIds);
            mission.setCompetences(competences);
        }

        return missionRepository.save(mission);
    }

    @Override
    public Optional<Mission> getMissionById(Long id) {
        return missionRepository.findById(id);
    }

    @Override
    public List<Mission> getAllMissionsActives() {
        return missionRepository.findByActiveTrue();
    }

    @Override
    public List<Mission> getMissionsByEntrepreneur(Long entrepreneurId) {
        return missionRepository.findByEntrepreneurId(entrepreneurId);
    }

    @Override
    public List<Mission> getMissionsMatchingProfil(Long profilId) {
        Profil profil = profilRepository.findById(profilId)
                .orElseThrow(() -> new IllegalArgumentException("Profil introuvable"));

        // Si le profil n'a pas de compétences, retourner toutes les missions actives
        if (profil.getCompetences() == null || profil.getCompetences().isEmpty()) {
            return missionRepository.findByActiveTrue();
        }

        // Sinon, faire le matching basé sur les compétences
        return missionRepository.findByCompetencesInAndActiveTrue(profil.getCompetences());
    }

    @Override
    public Mission updateMission(Mission mission) {
        return missionRepository.save(mission);
    }

    @Override
    public void desactiverMission(Long id) {
        Mission mission = missionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mission introuvable"));
        
        mission.setActive(false);
        missionRepository.save(mission);
    }

    @Override
    public void deleteMission(Long id) {
        missionRepository.deleteById(id);
    }
}
