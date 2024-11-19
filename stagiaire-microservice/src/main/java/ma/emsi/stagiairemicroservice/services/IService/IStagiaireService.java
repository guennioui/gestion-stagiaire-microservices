package ma.emsi.stagiairemicroservice.services.IService;

import ma.emsi.stagiairemicroservice.entities.Stagiaire;
import ma.emsi.stagiairemicroservice.exceptions.StagiaireNotFoundException;

import java.util.List;

public interface IStagiaireService {
    void addStagiaire(Stagiaire stagiaire);
    void removeStagiaire(String matricule) throws StagiaireNotFoundException;
    void updateStagiaire(String matricule, Stagiaire stagiaire) throws StagiaireNotFoundException;
    Stagiaire findByMatricule(String matricule) throws StagiaireNotFoundException;
    List<Stagiaire> getAll();
    
}
