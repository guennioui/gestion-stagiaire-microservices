package ma.emsi.departementmicroservice.controllers;

import ma.emsi.departementmicroservice.dtos.DepartementDto;
import ma.emsi.departementmicroservice.entities.Departement;
import ma.emsi.departementmicroservice.exceptions.DepartementAlreadyExistException;
import ma.emsi.departementmicroservice.exceptions.DepartementNotFoundException;
import ma.emsi.departementmicroservice.services.IService.IDepartementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/departement")
public class DepartementController {
    private final IDepartementService iDepartementService;

    public DepartementController(IDepartementService iDepartementService) {
        this.iDepartementService = iDepartementService;
    }


    @PostMapping(path = "/add-departement")
    public ResponseEntity<?> addDepartement (@RequestBody DepartementDto departementDto) throws DepartementNotFoundException, DepartementAlreadyExistException {
        this.iDepartementService.addDepartement(departementDto);
        return ResponseEntity.ok(departementDto);
    }
    @PutMapping(path = "/update/{code}")
    public ResponseEntity<?> updateDepartement(@PathVariable String code, @RequestBody DepartementDto departementDto)
            throws DepartementNotFoundException {
        this.iDepartementService.updateDepartement(code, departementDto);
        return ResponseEntity.ok(departementDto);
    }
    @DeleteMapping(path = "/delete/{code}")
    public ResponseEntity<Void> deleteDepartement(@PathVariable String code)
            throws DepartementNotFoundException {
        this.iDepartementService.deleteDepartement(code);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping(path = "/{code}")
    public ResponseEntity<DepartementDto> findDepartementByCode(@PathVariable String code)
            throws DepartementNotFoundException{
        Departement departement = this.iDepartementService.findDepartementBycode(code);
        DepartementDto result = this.iDepartementService.DepartementToDepartementDto(departement);
        return ResponseEntity.ok(result);
    }
    @GetMapping(path = "/all")
    public ResponseEntity<List<DepartementDto>> getAll(){
        List<DepartementDto> all = this.iDepartementService .getAll();
        return ResponseEntity.ok(all);
    }
}
