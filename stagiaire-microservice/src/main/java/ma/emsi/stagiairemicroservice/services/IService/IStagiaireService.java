package ma.emsi.stagiairemicroservice.services.IService;

import ma.emsi.stagiairemicroservice.dtos.StagiaireDto;
import ma.emsi.stagiairemicroservice.entities.Stagiaire;
import ma.emsi.stagiairemicroservice.exceptions.StagiaireNotFoundException;

import java.util.List;

public interface IStagiaireService {
    void addStagiaire(Stagiaire stagiaire);
    void removeStagiaire(String matricule) throws StagiaireNotFoundException;
    void updateStagiaire(String matricule, StagiaireDto stagiaireDto) throws StagiaireNotFoundException;
    Stagiaire findByMatricule(String matricule) throws StagiaireNotFoundException;
    List<StagiaireDto> getAll();
    StagiaireDto stagiaireToStagiaireDTO(Stagiaire stagiaire);
    void assignStageToStagiaire(String matricule, Long stageId)throws StagiaireNotFoundException;
    Stagiaire stagiaireDTOToStagiaire(StagiaireDto stagiaireDto);
    
}
