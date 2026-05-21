package org.example.projet.Repository;

import org.example.projet.Entites.Competence;
import org.example.projet.Entites.Profil;
import org.example.projet.Entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfilRepository extends JpaRepository<Profil, Long> {
    

    Optional<Profil> findByUtilisateur(Utilisateur utilisateur);

    Optional<Profil> findByUtilisateurId(Long utilisateurId);

    @Query("SELECT DISTINCT p FROM Profil p JOIN p.competences c WHERE c IN :competences")
    List<Profil> findByCompetencesIn(@Param("competences") List<Competence> competences);
    

    @Query("SELECT p FROM Profil p WHERE p.utilisateur.role = 'FREELANCE'")
    List<Profil> findAllFreelances();
}
