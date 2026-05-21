package org.example.projet.Entites;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"mission", "freelance"})
@EqualsAndHashCode(exclude = {"mission", "freelance"})
public class Candidature {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;


    @ManyToOne
    @JoinColumn(name = "freelance_id", nullable = false)
    private Profil freelance;


    @Column(nullable = false)
    private LocalDateTime datePostulation;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutCandidature statut;


    @Column(columnDefinition = "TEXT")
    private String messageMotivation;

    public Candidature(Mission mission, Profil freelance) {
        this.mission = mission;
        this.freelance = freelance;
        this.datePostulation = LocalDateTime.now();
        this.statut = StatutCandidature.EN_ATTENTE;
    }


    public enum StatutCandidature {
        EN_ATTENTE,    // Orange
        ACCEPTEE,      // Vert
        REFUSEE        // Rouge
    }
}
