package ma.emsi.stagemicroservice.services.IserviceImp;

import ma.emsi.stagemicroservice.dtos.StageDto;
import ma.emsi.stagemicroservice.exceptions.StageAlreadyExistingException;
import ma.emsi.stagemicroservice.exceptions.StageNotFoundException;
import ma.emsi.stagemicroservice.mappers.StageMapper;
import ma.emsi.stagemicroservice.entities.Stage;
import ma.emsi.stagemicroservice.repositories.StageRepository;
import ma.emsi.stagemicroservice.services.Iservice.IStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IStageServiceImpl implements IStageService {
    private final StageRepository stageRepository;
    @Autowired
    public IStageServiceImpl(StageRepository stageRepository) {
        this.stageRepository = stageRepository;
    }

    @Override
    public void addStage(StageDto stageDto) throws StageAlreadyExistingException {
        Stage findById = null;
        try {
            findById = this.findStageById(stageDto.getStageId());
        }catch (StageNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        if(findById != null){
            throw new StageAlreadyExistingException("Stage Already Exist!");
        }
        Stage stage = this.stageDtoToStage(stageDto);
        this.stageRepository.save(stage);
    }

    @Override
    public void deleteStage(Long stageId) throws StageNotFoundException {
        Stage stageById = this.findStageById(stageId);
        this.stageRepository.delete(stageById);
    }

    @Override
    public void updateStage(Long stageId, StageDto stage) throws StageNotFoundException{
        Stage stageById = this.findStageById(stageId);
        stageById.setTitle(stage.getTitle());
        stageById.setDescription(stage.getDescription());
        stageById.setStartDate(stage.getStartDate());
        stageById.setEndDate(stage.getEndDate());
        this.stageRepository.save(stageById);

    }

    @Override
    public Stage findStageById(Long stageId) throws StageNotFoundException {
        Optional<Stage> optionalStage = this.stageRepository.findById(stageId);
        if(optionalStage.isEmpty()){
            throw new StageNotFoundException("Stage not found Exception!");
        }
        return optionalStage.get();
    }

    @Override
    public List<StageDto> getAll() {
        List<StageDto> stageDtos = new ArrayList<>();
        for(Stage stage : this.stageRepository.findAll()){
            stageDtos.add(this.stageToStageDto(stage));
        }
        return stageDtos;
    }

    @Override
    public StageDto stageToStageDto(Stage stage) {
        return StageMapper.INSTANCE.stageToStageDto(stage);
    }

    @Override
    public Stage stageDtoToStage(StageDto stageDto) {
        return StageMapper.INSTANCE.stageDtoToStage(stageDto);
    }
}
