package ma.emsi.stagemicroservice.services.IserviceImp;

import ma.emsi.stagemicroservice.clients.DepartementRestClient;
import ma.emsi.stagemicroservice.clients.StagiaireRestClient;
import ma.emsi.stagemicroservice.dtos.DepartementDto;
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
    private final DepartementRestClient departementRestClient;

    @Autowired
    public IStageServiceImpl(StageRepository stageRepository, StagiaireRestClient stagiaireRestClient, DepartementRestClient departementRestClient) {
        this.stageRepository = stageRepository;
        this.stagiaireRestClient = stagiaireRestClient;
        this.departementRestClient = departementRestClient;
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
        Stage stageByStageId = null;
        if(optionalStage.isEmpty()){
            throw new StageNotFoundException("stage not found!");
        }else{
            stageByStageId = optionalStage.get();
            if(stageByStageId.getCodeDepartement() != null){
                ResponseEntity<DepartementDto> departementByCode = departementRestClient.findDepartementByCode(stageByStageId.getCodeDepartement());
                if(departementByCode.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))){
                    stageByStageId.setDepartementDto(departementByCode.getBody());
                }else{
                    throw new RuntimeException("une erreur s'est produite lors de l'appel du RestDepartementClient");
                }
            }
        }
        return this.stageToStageDto(stageByStageId);
    }

    @Override
    public List<StageDto> getAll() {//assign for each stage his departementDto
        List<StageDto> stageDtos = new ArrayList<>();
        List<DepartementDto> departementDtos = new ArrayList<>();
        ResponseEntity<List<DepartementDto>> departements = departementRestClient.getAll();
        if(departements.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))){
            departementDtos.addAll(departements.getBody());
        }
        for(Stage stage : this.stageRepository.findAll()){
            if(stage.getCodeDepartement() != null){
                for(DepartementDto departementDto : departementDtos){
                    if(departementDto.getCode().equals(stage.getCodeDepartement())){
                        stage.setDepartementDto(departementDto);
                    }
                }
            }
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

    @Override
    public void assignDepartementToStage(Long stageId, String codeDepartement) throws StageNotFoundException {
        StageDto stageById = this.findStageById(stageId);
        if(stageById != null){
            ResponseEntity<DepartementDto> departementByCode = departementRestClient.findDepartementByCode(codeDepartement);
            System.out.println("143: "+departementByCode.getStatusCode());
            System.out.println("143: "+departementByCode.getBody());
            if(departementByCode.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))){
                Stage stage = this.stageDtoToStage(stageById);
                stage.setCodeDepartement(codeDepartement);
                stage.setDepartementDto(departementByCode.getBody());
                this.stageRepository.save(stage);
            }else{
                throw new RuntimeException("une erreur s'est produite lors de l'appel du RestDepartementClient");
            }
        }else{
            throw new StageNotFoundException("stage not found exception");
        }
    }
}
