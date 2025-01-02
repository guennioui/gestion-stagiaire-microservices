package ma.emsi.departementmicroservice.controllers;

import ma.emsi.departementmicroservice.dtos.DepartementDto;
import ma.emsi.departementmicroservice.entities.Departement;
import ma.emsi.departementmicroservice.exceptions.DepartementAlreadyExistException;
import ma.emsi.departementmicroservice.exceptions.DepartementNotFoundException;
import ma.emsi.departementmicroservice.services.IService.IDepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/departement")
@CrossOrigin("*")
public class DepartementController {
    private final IDepartementService iDepartementService;

    @Autowired
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

    @GetMapping(path = "/get-all")
    public ResponseEntity<Map<String, Object>> findAllStage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<DepartementDto> stagePage = iDepartementService.getAll(page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("content", stagePage.getContent());
        response.put("currentPage", stagePage.getNumber());
        response.put("totalItems", stagePage.getTotalElements());
        response.put("totalPages", stagePage.getTotalPages());

        return ResponseEntity.ok(response);
    }
}
