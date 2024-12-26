package ma.emsi.stagiairemicroservice.services.IServiceImpl;

import ma.emsi.stagiairemicroservice.clients.StageRestClient;
import ma.emsi.stagiairemicroservice.dtos.StageDto;
import ma.emsi.stagiairemicroservice.dtos.StagiaireDto;
import ma.emsi.stagiairemicroservice.entities.Stagiaire;
import ma.emsi.stagiairemicroservice.exceptions.StagiaireNotFoundException;
import ma.emsi.stagiairemicroservice.mappers.StagiaireMapper;
import ma.emsi.stagiairemicroservice.repositories.StagiaireRepository;
import ma.emsi.stagiairemicroservice.services.IService.IStagiaireService;
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
public class IStagiaireServiceImpl implements IStagiaireService {
    private final StagiaireRepository stagiaireRepository;
    private final StageRestClient stageRestClient;
    @Autowired
    public IStagiaireServiceImpl(StagiaireRepository stagiaireRepository, StageRestClient stageRestClient) {
        this.stagiaireRepository = stagiaireRepository;
        this.stageRestClient = stageRestClient;
    }

    @Override
    public void addStagiaire(Stagiaire stagiaire) {
        this.stagiaireRepository.save(stagiaire);
    }

    @Override
    public void removeStagiaire(String matricule) throws StagiaireNotFoundException {
        this.stagiaireRepository.delete(
                this.findByMatricule(matricule)
        );
    }

    @Override
    public void updateStagiaire(String matricule, StagiaireDto stagiaireDto) throws StagiaireNotFoundException {
        Stagiaire optionalStagiaire = this.findByMatricule(matricule);
        System.out.println(optionalStagiaire);
        if(optionalStagiaire!=null){
            optionalStagiaire.setMatricule(stagiaireDto.getMatricule());
            optionalStagiaire.setFirstName(stagiaireDto.getFirstName());
            optionalStagiaire.setLastName(stagiaireDto.getLastName());
            optionalStagiaire.setEmail(stagiaireDto.getEmail());
            optionalStagiaire.setPhoneNumber(stagiaireDto.getPhoneNumber());
            optionalStagiaire.setSchoolName(stagiaireDto.getSchoolName());
            this.stagiaireRepository.save(optionalStagiaire);
        }
    }

    @Override
    public Stagiaire findByMatricule(String matricule) throws StagiaireNotFoundException {
        Optional<Stagiaire> optionalStagiaire = this.stagiaireRepository.findByMatricule(matricule);
        Stagiaire stagiaire = null;
        if(optionalStagiaire.isEmpty()){
            throw new StagiaireNotFoundException("le stagiaire que vous rechercher est introuvable!");
        }
        stagiaire = optionalStagiaire.get();
        if(stagiaire.getStageId() != null){
            ResponseEntity<StageDto> stageById = stageRestClient.findStageById(stagiaire.getStageId());
            if(stageById.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))) {
                StageDto stageDto = stageById.getBody();
                stagiaire.setStageDto(stageDto);
            }
        }
        return stagiaire;
    }

    @Override
    public List<StagiaireDto> getAll() {
        List<StagiaireDto> stagiaireDtos = new ArrayList<>();
        List<StageDto> stageDtos = new ArrayList<>();
        ResponseEntity<List<StageDto>> stages = stageRestClient.getAll();
        if(stages.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))){
            stageDtos.addAll(stages.getBody());
        }
        for(Stagiaire stagiaire : this.stagiaireRepository.findAll()){
            if(stagiaire.getStageId() != null){
                for(StageDto stageDto : stageDtos){
                    if(stageDto.getStageId() == stagiaire.getStageId()){
                        stagiaire.setStageDto(stageDto);
                    }
                }
            }
            stagiaireDtos.add(this.stagiaireToStagiaireDTO(stagiaire));
        }
        return stagiaireDtos;
    }

    @Override
    public void assignStageToStagiaire(String matricule, Long stageId) throws StagiaireNotFoundException {
        Stagiaire byMatricule = this.findByMatricule(matricule);
        if(byMatricule != null){
            ResponseEntity<StageDto> stageById = stageRestClient.findStageById(stageId);
            if(stageById.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(200))){
                byMatricule.setStageId(stageId);
                this.stagiaireRepository.save(byMatricule);
            }
            else {
                throw new RuntimeException("une erreur est survenue lors de l'affectation du stagiaire: " + matricule + " au stage: " + stageId);
            }
        }else{
            throw new StagiaireNotFoundException("le stagiaire que vous rechercher est introuvable!");
        }
    }

    @Override
    public List<StagiaireDto> getStagiairesByStageId(Long stageId) {
        List<StagiaireDto> stagiaireDtos = new ArrayList<>();
        for(Stagiaire stagiaire : this.stagiaireRepository.findAllByStageId(stageId)){
            stagiaireDtos.add(
                    this.stagiaireToStagiaireDTO(stagiaire)
            );
        }
        return stagiaireDtos;
    }

    @Override
    public StagiaireDto stagiaireToStagiaireDTO(Stagiaire stagiaire) {
        return StagiaireMapper.INSTANCE.stagiaireToStagiaireDTO(stagiaire);
    }

    @Override
    public Stagiaire stagiaireDTOToStagiaire(StagiaireDto stagiaireDto) {
        return StagiaireMapper.INSTANCE.stagiaireDTOToStagiaire(stagiaireDto);
    }
}
