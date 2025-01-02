package ma.emsi.encadrantmicroservice.services.IserviceImp;

import ma.emsi.encadrantmicroservice.dtos.EncadrantDto;
import ma.emsi.encadrantmicroservice.entities.Encadrant;
import ma.emsi.encadrantmicroservice.exceptions.EncadrantAlreadyExistException;
import ma.emsi.encadrantmicroservice.exceptions.EncadrantNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IEncadrantService {

    void addEncadrant(EncadrantDto encadrantDto) throws EncadrantNotFoundException, EncadrantAlreadyExistException;
    void deleteEncadrant(String matricule) throws EncadrantNotFoundException;
    void updateEncadrant(String matricule, EncadrantDto encadrantDto) throws EncadrantNotFoundException;
    List<EncadrantDto> getAll();
    Encadrant findEncadrantByMatricule(String matricule) throws EncadrantNotFoundException;
    Encadrant encadrantDtoToEncadrant(EncadrantDto encadrantDto);
    EncadrantDto encadrantToEncadrantDto(Encadrant encadrant);
    Page<EncadrantDto> getAll(int page, int size);
}
