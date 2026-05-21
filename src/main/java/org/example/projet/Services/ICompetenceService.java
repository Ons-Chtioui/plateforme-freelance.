package org.example.projet.Services;

import org.example.projet.Entites.Competence;

import java.util.List;
import java.util.Optional;

public interface ICompetenceService {
    

    Competence creerCompetence(String nom, String description);
    

    Optional<Competence> getCompetenceById(Long id);
    

    List<Competence> getAllCompetences();
    

    Competence updateCompetence(Competence competence);
    

    void deleteCompetence(Long id);
}
