package ma.emsi.departementmicroservice.services.IService;

import ma.emsi.departementmicroservice.dtos.DepartementDto;
import ma.emsi.departementmicroservice.entities.Departement;
import ma.emsi.departementmicroservice.exceptions.DepartementAlreadyExistException;
import ma.emsi.departementmicroservice.exceptions.DepartementNotFoundException;

import java.util.List;

public interface IDepartementService {
    void addDepartement(DepartementDto departementDto) throws DepartementNotFoundException, DepartementAlreadyExistException;
    void deleteDepartement(String code) throws DepartementNotFoundException;
    void updateDepartement(String code, DepartementDto departementDto) throws DepartementNotFoundException;
    List<DepartementDto> getAll();
    Departement findDepartementBycode(String code) throws DepartementNotFoundException;
    Departement DepartementDtoToDepartement(DepartementDto departementDto);
    DepartementDto DepartementToDepartementDto(Departement departement);

}
