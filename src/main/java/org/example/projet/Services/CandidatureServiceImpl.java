package org.example.projet.Services;

import lombok.AllArgsConstructor;
import org.example.projet.Entites.Candidature;
import org.example.projet.Entites.Entreprise;
import org.example.projet.Entites.Mission;
import org.example.projet.Entites.Profil;
import org.example.projet.Repository.CandidatureRepository;
import org.example.projet.Repository.EntrepriseRepository;
import org.example.projet.Repository.MissionRepository;
import org.example.projet.Repository.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CandidatureServiceImpl implements ICandidatureService {


    private CandidatureRepository candidatureRepository;
    

    private MissionRepository missionRepository;
    

    private ProfilRepository profilRepository;
    

    private EntrepriseRepository entrepriseRepository;

    @Override
    public Candidature postuler(Long missionId, Long freelanceId, String messageMotivation) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new IllegalArgumentException("Mission introuvable"));
        
        Profil freelance = profilRepository.findById(freelanceId)
                .orElseThrow(() -> new IllegalArgumentException("Profil freelance introuvable"));

        // Vérifier si le freelance a déjà postulé
        if (candidatureRepository.existsByMissionAndFreelance(mission, freelance)) {
            throw new IllegalStateException("Vous avez déjà postulé à cette mission");
        }

        Candidature candidature = new Candidature();
        candidature.setMission(mission);
        candidature.setFreelance(freelance);
        candidature.setDatePostulation(LocalDateTime.now());
        candidature.setStatut(Candidature.StatutCandidature.EN_ATTENTE);
        candidature.setMessageMotivation(messageMotivation);

        return candidatureRepository.save(candidature);
    }

    @Override
    public Optional<Candidature> getCandidatureById(Long id) {
        return candidatureRepository.findById(id);
    }

    @Override
    public List<Candidature> getCandidaturesFreelance(Long freelanceId) {
        Profil freelance = profilRepository.findById(freelanceId)
                .orElseThrow(() -> new IllegalArgumentException("Profil introuvable"));
        
        return candidatureRepository.findByFreelanceOrderByDatePostulationDesc(freelance);
    }

    @Override
    public List<Candidature> getCandidaturesMission(Long missionId) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new IllegalArgumentException("Mission introuvable"));
        
        return candidatureRepository.findByMissionOrderByDatePostulationDesc(mission);
    }

    @Override
    public List<Candidature> getCandidaturesEntrepreneur(Long entrepreneurId) {
        Entreprise entreprise = entrepriseRepository.findByEntrepreneurId(entrepreneurId)
                .orElseThrow(() -> new IllegalArgumentException("Entreprise introuvable"));
        
        return candidatureRepository.findByEntrepriseId(entreprise.getId());
    }

    @Override
    public Candidature accepterCandidature(Long candidatureId) {
        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() -> new IllegalArgumentException("Candidature introuvable"));
        
        candidature.setStatut(Candidature.StatutCandidature.ACCEPTEE);
        return candidatureRepository.save(candidature);
    }

    @Override
    public Candidature refuserCandidature(Long candidatureId) {
        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() -> new IllegalArgumentException("Candidature introuvable"));
        
        candidature.setStatut(Candidature.StatutCandidature.REFUSEE);
        return candidatureRepository.save(candidature);
    }

    @Override
    public boolean aDejaPostule(Long missionId, Long freelanceId) {
        Mission mission = missionRepository.findById(missionId).orElse(null);
        Profil freelance = profilRepository.findById(freelanceId).orElse(null);
        
        if (mission == null || freelance == null) {
            return false;
        }
        
        return candidatureRepository.existsByMissionAndFreelance(mission, freelance);
    }

    @Override
    public void deleteCandidature(Long id) {
        candidatureRepository.deleteById(id);
    }
}
