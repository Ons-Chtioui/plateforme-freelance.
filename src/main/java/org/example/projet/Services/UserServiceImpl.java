package org.example.projet.Services;

import lombok.AllArgsConstructor;
import org.example.projet.Entites.RoleType;
import org.example.projet.Entites.Utilisateur;
import org.example.projet.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements IUserService {


    private UtilisateurRepository utilisateurRepository;

    @Override
    public Utilisateur creerUtilisateur(String email, String password, RoleType role) {
        if (emailExists(email)) {
            throw new IllegalArgumentException("Cet email est déjà utilisé");
        }
        
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(email);
        utilisateur.setPassword(password); //  utiliser BCrypt
        utilisateur.setRole(role);
        
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public Optional<Utilisateur> authentifier(String email, String password) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByEmail(email);
        
        // En production: utiliser BCrypt pour comparer les mots de passe
        if (utilisateur.isPresent() && utilisateur.get().getPassword().equals(password)) {
            return utilisateur;
        }
        
        return Optional.empty();
    }

    @Override
    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id);
    }

    @Override
    public Optional<Utilisateur> getUtilisateurByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    @Override
    public List<Utilisateur> getUtilisateursByRole(RoleType role) {
        return utilisateurRepository.findByRole(role);
    }

    @Override
    public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    @Override
    public void deleteUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }

    @Override
    public boolean emailExists(String email) {
        return utilisateurRepository.existsByEmail(email);
    }
}
