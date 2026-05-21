package org.example.projet.Entites;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entité Profil - Contient les informations détaillées d'un utilisateur
 * Attributs spécifiques selon le rôle (FREELANCE ou ENTREPRENEUR)
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"utilisateur", "competences"})
@EqualsAndHashCode(exclude = {"utilisateur", "competences"})
public class Profil {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false)
    private String prenom;
    
    private String telephone;

    @Column(columnDefinition = "TEXT")
    private String biographie;

    private String photoProfil;


    private String cvPdf;

    @OneToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private Utilisateur utilisateur;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "profil_competences",
            joinColumns = @JoinColumn(name = "profil_id"),
            inverseJoinColumns = @JoinColumn(name = "competence_id")
    )
    private List<Competence> competences = new ArrayList<>();


    @OneToMany(mappedBy = "freelance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Candidature> candidatures = new ArrayList<>();


    public String getNomComplet() {
        return prenom + " " + nom;
    }
}
