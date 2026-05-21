package org.example.projet.Services;

import org.example.projet.Entites.RoleType;
import org.example.projet.Entites.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    

    Utilisateur creerUtilisateur(String email, String password, RoleType role);
    

    Optional<Utilisateur> authentifier(String email, String password);
    

    Optional<Utilisateur> getUtilisateurById(Long id);
    

    Optional<Utilisateur> getUtilisateurByEmail(String email);
    

    List<Utilisateur> getAllUtilisateurs();
    

    List<Utilisateur> getUtilisateursByRole(RoleType role);
    

    Utilisateur updateUtilisateur(Utilisateur utilisateur);
    

    void deleteUtilisateur(Long id);
    

    boolean emailExists(String email);
}
