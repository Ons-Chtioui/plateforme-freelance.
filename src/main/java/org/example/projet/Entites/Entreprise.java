package org.example.projet.Entites;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"entrepreneur", "missions"})
@EqualsAndHashCode(exclude = {"entrepreneur", "missions"})
public class Entreprise {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false)
    private String secteurActivite;
    
    private String siteWeb;
    
    @Column(columnDefinition = "TEXT")
    private String description;


    @OneToOne
    @JoinColumn(name = "entrepreneur_id", nullable = false, unique = true)
    private Utilisateur entrepreneur;


    @OneToMany(mappedBy = "entreprise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mission> missions = new ArrayList<>();
}
