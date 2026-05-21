package org.example.projet.Repository;

import org.example.projet.Entites.Competence;
import org.example.projet.Entites.Entreprise;
import org.example.projet.Entites.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
    

    List<Mission> findByEntrepriseOrderByIdDesc(Entreprise entreprise);

    List<Mission> findByActiveTrue();

    @Query("SELECT DISTINCT m FROM Mission m JOIN m.competences c WHERE c IN :competences AND m.active = true")
    List<Mission> findByCompetencesInAndActiveTrue(@Param("competences") List<Competence> competences);
    

    @Query("SELECT m FROM Mission m WHERE m.entreprise.entrepreneur.id = :entrepreneurId ORDER BY m.id DESC")
    List<Mission> findByEntrepreneurId(@Param("entrepreneurId") Long entrepreneurId);

    long countByEntrepriseAndActiveTrue(Entreprise entreprise);
}
