package ma.emsi.departementmicroservice.services.IServicesImp;

import ma.emsi.departementmicroservice.dtos.DepartementDto;
import ma.emsi.departementmicroservice.entities.Departement;
import ma.emsi.departementmicroservice.exceptions.DepartementAlreadyExistException;
import ma.emsi.departementmicroservice.exceptions.DepartementNotFoundException;
import ma.emsi.departementmicroservice.mappers.DepartementMapper;
import ma.emsi.departementmicroservice.repositories.DepartementRepository;
import ma.emsi.departementmicroservice.services.IService.IDepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IDepartementServiceImpl implements IDepartementService {
    private final DepartementRepository departementRepository;

    public IDepartementServiceImpl(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    @Override
    public void addDepartement(DepartementDto departementDto) throws DepartementNotFoundException, DepartementAlreadyExistException {
        System.out.println(departementDto);
        Departement findbyId = null;
        try{
            findbyId = this.findDepartementBycode(departementDto.getCode());
        }catch (DepartementNotFoundException ex){
            System.out.println(ex.getMessage());
        }
        if(findbyId!=null){
            throw new DepartementAlreadyExistException("Departement Already existing");
        }
        Departement encadrant = this.DepartementDtoToDepartement(departementDto);
        System.out.println(encadrant);
        this.departementRepository.save(encadrant);
    }

    @Override
    public void deleteDepartement(String code) throws DepartementNotFoundException {
        Departement departementId= this.findDepartementBycode(code);
        this.departementRepository.delete(departementId);
    }

    @Override
    public void updateDepartement(String code, DepartementDto departementDto) throws DepartementNotFoundException {
        Departement departementId = this.findDepartementBycode(code);
        departementId.setName((departementDto.getName()));
        departementId.setDescription(departementDto.getDescription());
        departementId.setCode(departementDto.getCode());
        this.departementRepository.save(departementId);
    }

    @Override
    public List<DepartementDto> getAll() {
        List<DepartementDto> departementDtos = new ArrayList<>();
        for(Departement departement : this.departementRepository.findAll()){
            departementDtos.add(this.DepartementToDepartementDto(departement));
        }
        return departementDtos;
    }

    @Override
    public Page<DepartementDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Departement> departementPage = departementRepository.findAll(pageable);
        return departementPage.map(this::DepartementToDepartementDto);
    }

    @Override
    public Departement findDepartementBycode(String code) throws DepartementNotFoundException {
        Optional<Departement> optionalDepartement = this.departementRepository.findByCode(code);
        if(optionalDepartement.isEmpty()){
            throw new DepartementNotFoundException("Departement not found exception!");
        }
        return optionalDepartement.get();
    }

    @Override
    public Departement DepartementDtoToDepartement(DepartementDto departementDto) {
        return DepartementMapper.INSTANCE.DepartementDtoToDepartment(departementDto);
    }

    @Override
    public DepartementDto DepartementToDepartementDto(Departement departement) {
        return DepartementMapper.INSTANCE.DepartementToDepartmentDto(departement);
    }
}
