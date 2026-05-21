package org.example.projet.Services;

import org.example.projet.Entites.Competence;
import org.example.projet.Entites.Mission;
import org.example.projet.Entites.Profil;

import java.util.List;
import java.util.Optional;

public interface IMissionService {
    

    Mission creerMission(Long entrepreneurId, String titre, String description, double budget, List<Long> competenceIds);
    

    Optional<Mission> getMissionById(Long id);

    List<Mission> getAllMissionsActives();
    

    List<Mission> getMissionsByEntrepreneur(Long entrepreneurId);
    

    List<Mission> getMissionsMatchingProfil(Long profilId);
    

    Mission updateMission(Mission mission);
    

    void desactiverMission(Long id);
    

    void deleteMission(Long id);
}
