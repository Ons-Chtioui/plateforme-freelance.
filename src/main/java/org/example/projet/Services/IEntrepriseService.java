package org.example.projet.Services;

import org.example.projet.Entites.Entreprise;
import org.example.projet.Entites.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface IEntrepriseService {
    

    Entreprise creerEntreprise(Long entrepreneurId, String nom, String secteurActivite, String siteWeb, String description);
    

    Optional<Entreprise> getEntrepriseById(Long id);
    

    Optional<Entreprise> getEntrepriseByEntrepreneur(Long entrepreneurId);
    

    List<Entreprise> getAllEntreprises();
    

    Entreprise updateEntreprise(Entreprise entreprise);

    void deleteEntreprise(Long id);
    

    boolean entrepreneurHasEntreprise(Long entrepreneurId);
}
