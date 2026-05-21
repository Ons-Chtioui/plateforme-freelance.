package org.example.projet.Entites;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"missions", "profils"})
@EqualsAndHashCode(exclude = {"missions", "profils"})
public class Competence {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String nom;
    
    @Column(columnDefinition = "TEXT")
    private String description;


    @ManyToMany(mappedBy = "competences")
    private List<Mission> missions = new ArrayList<>();


    @ManyToMany(mappedBy = "competences")
    private List<Profil> profils = new ArrayList<>();
}
