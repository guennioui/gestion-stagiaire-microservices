package ma.emsi.stagiairemicroservice.services.IService;

import ma.emsi.stagiairemicroservice.dtos.StagiaireDto;
import ma.emsi.stagiairemicroservice.entities.Stagiaire;
import ma.emsi.stagiairemicroservice.exceptions.StagiaireNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IStagiaireService {
    void addStagiaire(Stagiaire stagiaire);
    void removeStagiaire(String matricule) throws StagiaireNotFoundException;
    void updateStagiaire(String matricule, StagiaireDto stagiaireDto) throws StagiaireNotFoundException;
    Stagiaire findByMatricule(String matricule) throws StagiaireNotFoundException;
    List<StagiaireDto> getAll();
    Page<StagiaireDto> getAll(int page, int size);
    void assignStageToStagiaire(String matricule, Long stageId)throws StagiaireNotFoundException;
    List<StagiaireDto> getStagiairesByStageId(Long stageId);
    StagiaireDto stagiaireToStagiaireDTO(Stagiaire stagiaire);
    Stagiaire stagiaireDTOToStagiaire(StagiaireDto stagiaireDto);
    
}
