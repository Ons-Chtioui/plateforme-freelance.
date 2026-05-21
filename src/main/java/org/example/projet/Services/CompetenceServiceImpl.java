package org.example.projet.Services;

import lombok.AllArgsConstructor;
import org.example.projet.Entites.Competence;
import org.example.projet.Repository.CompetenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CompetenceServiceImpl implements ICompetenceService {


    private CompetenceRepository competenceRepository;

    @Override
    public Competence creerCompetence(String nom, String description) {
        if (competenceRepository.existsByNom(nom)) {
            throw new IllegalArgumentException("Cette compétence existe déjà");
        }

        Competence competence = new Competence();
        competence.setNom(nom);
        competence.setDescription(description);

        return competenceRepository.save(competence);
    }

    @Override
    public Optional<Competence> getCompetenceById(Long id) {
        return competenceRepository.findById(id);
    }

    @Override
    public List<Competence> getAllCompetences() {
        return competenceRepository.findAll();
    }

    @Override
    public Competence updateCompetence(Competence competence) {
        return competenceRepository.save(competence);
    }

    @Override
    public void deleteCompetence(Long id) {
        competenceRepository.deleteById(id);
    }
}
