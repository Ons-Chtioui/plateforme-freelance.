package org.example.projet.Entites;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"profil", "entrepriseGeree"})
@EqualsAndHashCode(exclude = {"profil", "entrepriseGeree"})
public class Utilisateur {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @OneToOne(mappedBy = "utilisateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private Profil profil;

    @OneToOne(mappedBy = "entrepreneur", cascade = CascadeType.ALL, orphanRemoval = true)
    private Entreprise entrepriseGeree;
}
