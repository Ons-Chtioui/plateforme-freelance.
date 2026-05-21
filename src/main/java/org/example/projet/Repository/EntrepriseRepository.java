package org.example.projet.Repository;

import org.example.projet.Entites.Entreprise;
import org.example.projet.Entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {
    

    Optional<Entreprise> findByEntrepreneur(Utilisateur entrepreneur);
    

    Optional<Entreprise> findByEntrepreneurId(Long entrepreneurId);
    

    boolean existsByEntrepreneur(Utilisateur entrepreneur);

    Optional<Entreprise> findByNom(String nom);
}
