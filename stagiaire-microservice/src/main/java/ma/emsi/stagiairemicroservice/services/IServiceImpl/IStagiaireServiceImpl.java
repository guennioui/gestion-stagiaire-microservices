package ma.emsi.stagiairemicroservice.services.IServiceImpl;

import ma.emsi.stagiairemicroservice.dtos.StagiaireDto;
import ma.emsi.stagiairemicroservice.entities.Stagiaire;
import ma.emsi.stagiairemicroservice.exceptions.StagiaireNotFoundException;
import ma.emsi.stagiairemicroservice.mappers.StagiaireMapper;
import ma.emsi.stagiairemicroservice.repositories.StagiaireRepository;
import ma.emsi.stagiairemicroservice.services.IService.IStagiaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IStagiaireServiceImpl implements IStagiaireService {
    private final StagiaireRepository stagiaireRepository;

    @Autowired
    public IStagiaireServiceImpl(StagiaireRepository stagiaireRepository) {
        this.stagiaireRepository = stagiaireRepository;
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
        if(optionalStagiaire.isEmpty()){
            throw new StagiaireNotFoundException("le stagiaire que vous rechercher est introuvable!");
        }
        return optionalStagiaire.get();
    }

    @Override
    public List<StagiaireDto> getAll() {
        List<StagiaireDto> stagiaireDtos = new ArrayList<>();
        for(Stagiaire stagiaire : this.stagiaireRepository.findAll()){
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
