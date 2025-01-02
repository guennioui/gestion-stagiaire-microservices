package ma.emsi.stagemicroservice.services.IserviceImp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ma.emsi.stagemicroservice.clients.DepartementRestClient;
import ma.emsi.stagemicroservice.clients.EncadrantRestClient;
import ma.emsi.stagemicroservice.clients.StagiaireRestClient;
import ma.emsi.stagemicroservice.dtos.DepartementDto;
import ma.emsi.stagemicroservice.dtos.EncadrantDto;
import ma.emsi.stagemicroservice.dtos.StageDto;
import ma.emsi.stagemicroservice.dtos.StagiaireDto;
import ma.emsi.stagemicroservice.exceptions.StageAlreadyExistingException;
import ma.emsi.stagemicroservice.exceptions.StageNotFoundException;
import ma.emsi.stagemicroservice.mappers.StageMapper;
import ma.emsi.stagemicroservice.entities.Stage;
import ma.emsi.stagemicroservice.repositories.StageRepository;
import ma.emsi.stagemicroservice.services.Iservice.IStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class IStageServiceImpl implements IStageService {
    private final StageRepository stageRepository;
    private final StagiaireRestClient stagiaireRestClient;
    private final DepartementRestClient departementRestClient;
    private final EncadrantRestClient encadrantRestClient;

    @Autowired
    public IStageServiceImpl(StageRepository stageRepository, StagiaireRestClient stagiaireRestClient, DepartementRestClient departementRestClient, EncadrantRestClient encadrantRestClient) {
        this.stageRepository = stageRepository;
        this.stagiaireRestClient = stagiaireRestClient;
        this.departementRestClient = departementRestClient;
        this.encadrantRestClient = encadrantRestClient;
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
            if(stageByStageId.getMatriculeEncadrant() != null){
                ResponseEntity<EncadrantDto> encadrantByMatricule = encadrantRestClient.findEncadrantByMatricule(stageByStageId.getMatriculeEncadrant());
                if(encadrantByMatricule.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))){
                    stageByStageId.setEncadrantDto(encadrantByMatricule.getBody());
                }else{
                    throw new RuntimeException("une erreur s'est produite lors de l'appel du RestEncadrantClient");
                }
            }
        }
        return this.stageToStageDto(stageByStageId);
    }

    @Override
    public List<StageDto> getAll() {//assign for each stage his departementDto
        List<StageDto> stageDtos = new ArrayList<>();
        List<DepartementDto> departementDtos = new ArrayList<>();
        List<EncadrantDto> encadrantDtos = new ArrayList<>();

        ResponseEntity<Map<String, Object>> departements = departementRestClient.getAll();
        ResponseEntity<List<EncadrantDto>> encadrants = encadrantRestClient.getAll();

        if(departements.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))){
            ObjectMapper mapper = new ObjectMapper();
            List<LinkedHashMap<String, Object>> content = (List<LinkedHashMap<String, Object>>) departements.getBody().get("content");
            List<DepartementDto> dtos = content.stream()
                    .map(obj -> mapper.convertValue(obj, DepartementDto.class))
                    .collect(Collectors.toList());
            departementDtos.addAll(dtos);
        }

        if(encadrants.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))){
            encadrantDtos.addAll(encadrants.getBody());
        }
        System.out.println("141: "+departementDtos);
        System.out.println("142: "+encadrantDtos);
        System.out.println("143: "+departements);
        System.out.println("144: "+encadrants);

        for(Stage stage : this.stageRepository.findAll()){
            if(stage.getCodeDepartement() != null){
                for(DepartementDto departementDto : departementDtos){
                    if(departementDto.getCode().equals(stage.getCodeDepartement())){
                        stage.setDepartementDto(departementDto);
                    }
                }
            }
            if(stage.getMatriculeEncadrant() != null){
                for(EncadrantDto encadrantDto : encadrantDtos){
                    if(encadrantDto.getMatricule().equals(stage.getMatriculeEncadrant())){
                        stage.setEncadrantDto(encadrantDto);
                    }
                }
            }
            stageDtos.add(this.stageToStageDto(stage));
        }
        return stageDtos;
    }

    @Override
    public Page<StageDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Stage> stagePage = stageRepository.findAll(pageable);
        List<DepartementDto> departementDtos = new ArrayList<>();
        List<EncadrantDto> encadrantDtos = new ArrayList<>();

        ResponseEntity<Map<String, Object>> departements = departementRestClient.getAll();
        ResponseEntity<List<EncadrantDto>> encadrants = encadrantRestClient.getAll();

        if(departements.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))
                && encadrants.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))){
            List<LinkedHashMap<String, Object>> departement_content = (List<LinkedHashMap<String, Object>>) departements.getBody().get("content");
            ObjectMapper mapper = new ObjectMapper();
            departementDtos.addAll(departement_content.stream().map(obj -> mapper.convertValue(obj, DepartementDto.class)).collect(Collectors.toList()));
            encadrantDtos.addAll(encadrants.getBody());
        }

        return stagePage.map(stage->{
            if(stage.getCodeDepartement() != null && stage.getMatriculeEncadrant() != null){
                departementDtos
                        .stream()
                        .filter(departementDto -> departementDto.getCode().equals(stage.getCodeDepartement()))
                        .findFirst()
                        .ifPresent(stage::setDepartementDto);
                encadrantDtos
                        .stream()
                        .filter(encadrantDto -> encadrantDto.getMatricule().equals(stage.getMatriculeEncadrant()))
                        .findFirst()
                        .ifPresent(stage::setEncadrantDto);
            }
        return stageToStageDto(stage);
        });
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

    @Override
    public void assignEncadrantToStage(Long stageId, String matriculeEncadrant) throws StageNotFoundException {
        StageDto stageById = this.findStageById(stageId);
        if(stageById != null){
            ResponseEntity<EncadrantDto> encadrantByMatricule = encadrantRestClient.findEncadrantByMatricule(matriculeEncadrant);
            if(encadrantByMatricule.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))){
                Stage stage = this.stageDtoToStage(stageById);
                stage.setMatriculeEncadrant(matriculeEncadrant);
                stage.setEncadrantDto(encadrantByMatricule.getBody());
                this.stageRepository.save(stage);
            }else{
                throw new RuntimeException("une erreur s'est produite lors de l'appel du RestEncadrantClient");
            }
        }else{
                throw new StageNotFoundException("stage not found exception");
        }

    }
}
