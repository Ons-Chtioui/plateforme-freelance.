package org.example.projet.Services;

import lombok.AllArgsConstructor;
import org.example.projet.Entites.Entreprise;
import org.example.projet.Entites.Utilisateur;
import org.example.projet.Repository.EntrepriseRepository;
import org.example.projet.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class EntrepriseServiceImpl implements IEntrepriseService {


    private EntrepriseRepository entrepriseRepository;
    

    private UtilisateurRepository utilisateurRepository;

    @Override
    public Entreprise creerEntreprise(Long entrepreneurId, String nom, String secteurActivite, String siteWeb, String description) {
        Utilisateur entrepreneur = utilisateurRepository.findById(entrepreneurId)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));

        // Vérifier que l'utilisateur est bien un entrepreneur
        if (entrepreneur.getRole() != org.example.projet.Entites.RoleType.ENTREPRENEUR) {
            throw new IllegalStateException("Seuls les entrepreneurs peuvent créer une entreprise");
        }

        // Vérifier qu'il n'a pas déjà une entreprise
        if (entrepriseRepository.existsByEntrepreneur(entrepreneur)) {
            throw new IllegalStateException("Vous avez déjà une entreprise");
        }

        Entreprise entreprise = new Entreprise();
        entreprise.setNom(nom);
        entreprise.setSecteurActivite(secteurActivite);
        entreprise.setSiteWeb(siteWeb);
        entreprise.setDescription(description);
        entreprise.setEntrepreneur(entrepreneur);

        return entrepriseRepository.save(entreprise);
    }

    @Override
    public Optional<Entreprise> getEntrepriseById(Long id) {
        return entrepriseRepository.findById(id);
    }

    @Override
    public Optional<Entreprise> getEntrepriseByEntrepreneur(Long entrepreneurId) {
        return entrepriseRepository.findByEntrepreneurId(entrepreneurId);
    }

    @Override
    public List<Entreprise> getAllEntreprises() {
        return entrepriseRepository.findAll();
    }

    @Override
    public Entreprise updateEntreprise(Entreprise entreprise) {
        return entrepriseRepository.save(entreprise);
    }

    @Override
    public void deleteEntreprise(Long id) {
        entrepriseRepository.deleteById(id);
    }

    @Override
    public boolean entrepreneurHasEntreprise(Long entrepreneurId) {
        Utilisateur entrepreneur = utilisateurRepository.findById(entrepreneurId).orElse(null);
        if (entrepreneur == null) {
            return false;
        }
        return entrepriseRepository.existsByEntrepreneur(entrepreneur);
    }
}
