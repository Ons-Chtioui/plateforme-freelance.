package org.example.projet.Services;

import org.example.projet.Entites.Candidature;
import org.example.projet.Entites.Mission;
import org.example.projet.Entites.Profil;

import java.util.List;
import java.util.Optional;

public interface ICandidatureService {
    

    Candidature postuler(Long missionId, Long freelanceId, String messageMotivation);
    

    Optional<Candidature> getCandidatureById(Long id);
    

    List<Candidature> getCandidaturesFreelance(Long freelanceId);
    

    List<Candidature> getCandidaturesMission(Long missionId);
    

    List<Candidature> getCandidaturesEntrepreneur(Long entrepreneurId);
    

    Candidature accepterCandidature(Long candidatureId);
    

    Candidature refuserCandidature(Long candidatureId);
    

    boolean aDejaPostule(Long missionId, Long freelanceId);
    

    void deleteCandidature(Long id);
}
