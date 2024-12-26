package ma.emsi.stagemicroservice.services.Iservice;

import ma.emsi.stagemicroservice.dtos.StageDto;
import ma.emsi.stagemicroservice.exceptions.StageAlreadyExistingException;
import ma.emsi.stagemicroservice.exceptions.StageNotFoundException;
import ma.emsi.stagemicroservice.entities.Stage;

import java.util.List;

public interface IStageService {
    void addStage (StageDto stageDto) throws StageNotFoundException, StageAlreadyExistingException;
    void deleteStage(Long stageId) throws StageNotFoundException;
    void updateStage(Long stageId, StageDto stage) throws StageNotFoundException;
    StageDto findStageById(Long stageId) throws StageNotFoundException;
    List<StageDto> getAll();
    StageDto stageToStageDto(Stage stage);
    Stage stageDtoToStage(StageDto stageDto);
    void assignDepartementToStage(Long stageId, String codeDepartement) throws StageNotFoundException;
}
