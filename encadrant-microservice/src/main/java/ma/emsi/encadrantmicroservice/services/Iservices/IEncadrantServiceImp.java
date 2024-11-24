package ma.emsi.encadrantmicroservice.services.Iservices;

import ma.emsi.encadrantmicroservice.dtos.EncadrantDto;
import ma.emsi.encadrantmicroservice.entities.Encadrant;
import ma.emsi.encadrantmicroservice.exceptions.EncadrantAlreadyExistException;
import ma.emsi.encadrantmicroservice.exceptions.EncadrantNotFoundException;
import ma.emsi.encadrantmicroservice.mappers.EncadrantMapper;
import ma.emsi.encadrantmicroservice.repositories.EncadrantRepository;
import ma.emsi.encadrantmicroservice.services.IserviceImp.IEncadrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IEncadrantServiceImp implements IEncadrantService {
    private final EncadrantRepository encadrantRepository;
    @Autowired
    public IEncadrantServiceImp(EncadrantRepository encadrantRepository) {
        this.encadrantRepository = encadrantRepository;
    }

    @Override
    public void addEncadrant(EncadrantDto encadrantDto) throws EncadrantNotFoundException, EncadrantAlreadyExistException {
        System.out.println(encadrantDto);
        Encadrant findbyId = null;
        try{
            findbyId = this.findEncadrantByMatricule(encadrantDto.getMatricule());
        }catch (EncadrantNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        if(findbyId!=null){
            throw new EncadrantAlreadyExistException("Encadrant Already existing");
        }
        Encadrant encadrant = this.encadrantDtoToEncadrant(encadrantDto);
        System.out.println(encadrant);
        this.encadrantRepository.save(encadrant);
    }

    @Override
    public void deleteEncadrant(Long matricule) throws EncadrantNotFoundException {
        Encadrant encadrantId = this.findEncadrantByMatricule(matricule);
        this.encadrantRepository.delete(encadrantId);
    }

    @Override
    public void updateEncadrant( Long matricule, EncadrantDto encadrantDto) throws EncadrantNotFoundException {
        Encadrant encadrantId = this.findEncadrantByMatricule(matricule);
        encadrantId.setMatricule((encadrantDto.getMatricule()));
        encadrantId.setNom(encadrantDto.getNom());
        encadrantId.setPrenom(encadrantDto.getPrenom());
        encadrantId.setTelephone(encadrantDto.getTelephone());
        encadrantId.setEmail(encadrantDto.getEmail());
        encadrantId.setSpecialite(encadrantDto.getSpecialite());
        this.encadrantRepository.save(encadrantId);
    }

    @Override
    public List<EncadrantDto> getAll() {
        List<EncadrantDto> encadrantDtos = new ArrayList<>();
        for(Encadrant encadrant : this.encadrantRepository.findAll()){
            encadrantDtos.add(this.encadrantToEncadrantDto(encadrant));
        }
        return encadrantDtos;
    }

    @Override
    public Encadrant findEncadrantByMatricule(Long matricule) throws EncadrantNotFoundException {
        Optional<Encadrant> optionalEncadrant = this.encadrantRepository.findByMatricule(matricule);
        if(optionalEncadrant.isEmpty()){
            throw new EncadrantNotFoundException("Encadrand not found exception!");
        }
        return optionalEncadrant.get();
    }

    @Override
    public EncadrantDto encadrantToEncadrantDto(Encadrant encadrant) {
        return EncadrantMapper.INSTANCE.encadrantToEncadrantDto(encadrant);

    }

    @Override
    public Encadrant encadrantDtoToEncadrant(EncadrantDto encadrantDto) {
        return EncadrantMapper.INSTANCE.encadrantDtoToEncadrant(encadrantDto);
    }
}
