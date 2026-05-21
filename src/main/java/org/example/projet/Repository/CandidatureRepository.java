package org.example.projet.Repository;


import org.example.projet.Entites.Candidature;
import org.example.projet.Entites.Mission;
import org.example.projet.Entites.Profil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidatureRepository extends JpaRepository<Candidature, Long> {

    List<Candidature> findByFreelanceOrderByDatePostulationDesc(Profil freelance);

    List<Candidature> findByMissionOrderByDatePostulationDesc(Mission mission);
    

    @Query("SELECT c FROM Candidature c WHERE c.mission.entreprise.id = :entrepriseId ORDER BY c.datePostulation DESC")
    List<Candidature> findByEntrepriseId(@Param("entrepriseId") Long entrepriseId);

    boolean existsByMissionAndFreelance(Mission mission, Profil freelance);
    

    Optional<Candidature> findByMissionAndFreelance(Mission mission, Profil freelance);

    @Query("SELECT COUNT(c) FROM Candidature c WHERE c.mission.id = :missionId AND c.statut = :statut")
    long countByMissionIdAndStatut(@Param("missionId") Long missionId, @Param("statut") Candidature.StatutCandidature statut);
}
