package ma.emsi.stagemicroservice.services.IserviceImp;

import ma.emsi.stagemicroservice.clients.StagiaireRestClient;
import ma.emsi.stagemicroservice.dtos.StageDto;
import ma.emsi.stagemicroservice.dtos.StagiaireDto;
import ma.emsi.stagemicroservice.exceptions.StageAlreadyExistingException;
import ma.emsi.stagemicroservice.exceptions.StageNotFoundException;
import ma.emsi.stagemicroservice.mappers.StageMapper;
import ma.emsi.stagemicroservice.entities.Stage;
import ma.emsi.stagemicroservice.repositories.StageRepository;
import ma.emsi.stagemicroservice.services.Iservice.IStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IStageServiceImpl implements IStageService {
    private final StageRepository stageRepository;
    private final StagiaireRestClient stagiaireRestClient;

    @Autowired
    public IStageServiceImpl(StageRepository stageRepository, StagiaireRestClient stagiaireRestClient) {
        this.stageRepository = stageRepository;
        this.stagiaireRestClient = stagiaireRestClient;
    }

    @Override
    public void addStage(StageDto stageDto) throws StageAlreadyExistingException {
        StageDto findById = null;
        try {
            findById = this.findStageById(stageDto.getStageId());
        }catch (StageNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        if(findById != null){
            throw new StageAlreadyExistingException("Stage Already Exist!");
        }
        this.stageRepository.save(this.stageDtoToStage(stageDto));
    }

    @Override
    public void deleteStage(Long stageId) throws StageNotFoundException {
        StageDto findById = null;
        try {
            findById = this.findStageById(stageId);
        }catch (StageNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        if(findById == null){
            throw new StageNotFoundException("Stage Not Found Exception!");
        }
        //System.out.println(findById);
        Stage result = this.stageDtoToStage(findById);
        //System.out.println(result);
        this.stageRepository.delete(result);
    }

    @Override
    public void updateStage(Long stageId, StageDto stage) throws StageNotFoundException{
        StageDto findById = null;
        try {
            findById = this.findStageById(stageId);
        }catch (StageNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        if(findById == null){
            throw new StageNotFoundException("Stage Not Found Exception!");
        }
        Stage result = this.stageDtoToStage(findById);
        result.setTitle(stage.getTitle());
        result.setDescription(stage.getDescription());
        result.setStartDate(stage.getStartDate());
        result.setEndDate(stage.getEndDate());
        //result.setStagiaire(stage.getStagiaire());
        this.stageRepository.save(result);
    }

    @Override
    public StageDto findStageById(Long stageId) throws StageNotFoundException {
        Optional<Stage> optionalStage = this.stageRepository.findByStageId(stageId);
        if(optionalStage.isEmpty()){
            throw new StageNotFoundException("stage not found!");
        }
        StageDto stageDto = this.stageToStageDto(optionalStage.get());
        return stageDto;
    }

    /*@Override
    public StageDto findStageById(Long stageId) throws StageNotFoundException {
        Optional<Stage> optionalStage = this.stageRepository.findByStageId(stageId);
        if(optionalStage.isEmpty()){
            throw new StageNotFoundException("stage not found!");
        }
        //System.out.println("91: "+optionalStage.get());
        if(optionalStage.get().getStagiaireId() != null){
            ResponseEntity<StagiaireDto> stagiaireByMatricule = this.stagiaireRestClient.findStagiaireByMatricule(optionalStage.get().getStagiaireId());
            if(stagiaireByMatricule == null){
                throw new RuntimeException("une erreur est survenue lors de l'appel du stagiaireRestClient");
            }
            Stage stage = optionalStage.get();
            System.out.println("97: "+stage);
            if(stagiaireByMatricule.getBody() != null){
                stage.setStagiaire(List.of(stagiaireByMatricule.getBody()));
            }
            System.out.println("99: "+stage);
            StageDto stageDto = this.stageToStageDto(optionalStage.get());
            System.out.println("101: "+stageDto);
            return stageDto;
        }
        StageDto result = stageToStageDto(optionalStage.get());
        System.out.println("101: "+result);
        return result;
    }*/

    @Override
    public StageDto findStageByStagiaireId(Long StagiaireId) throws StageNotFoundException {
        return null;
    }

    @Override
    public void assignStagiaireToStage(String matricule, Long stageId) throws StageNotFoundException {
        ResponseEntity<StagiaireDto> stagiaireByMatricule = this.stagiaireRestClient.findStagiaireByMatricule(matricule);
        if(stagiaireByMatricule.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))){
            StageDto findById = null;
            try {
                findById = this.findStageById(stageId);
            }catch (StageNotFoundException ex){
                System.out.println(ex.getMessage());
            }
            if(findById == null){
                throw new StageNotFoundException("Stage Not Found Exception!");
            }
            Stage result = this.stageDtoToStage(findById);
            result.getStagiaireIds().add(matricule);
            this.stageRepository.save(result);
        }else{
            System.out.println("une erreur est survenue lors de l'appel du stagiaireRestClient");
        }
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
