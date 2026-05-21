package org.example.projet.Repository;

import org.example.projet.Entites.RoleType;
import org.example.projet.Entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    

    Optional<Utilisateur> findByEmail(String email);
    

    boolean existsByEmail(String email);

    List<Utilisateur> findByRole(RoleType role);

    long countByRole(RoleType role);
}
