package ma.emsi.encadrantmicroservice.controllers;

import ma.emsi.encadrantmicroservice.dtos.EncadrantDto;
import ma.emsi.encadrantmicroservice.entities.Encadrant;
import ma.emsi.encadrantmicroservice.exceptions.EncadrantAlreadyExistException;
import ma.emsi.encadrantmicroservice.exceptions.EncadrantNotFoundException;
import ma.emsi.encadrantmicroservice.services.IserviceImp.IEncadrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/encadrant")
public class EncadrantController {
    private final IEncadrantService iEncadrantService;
    @Autowired
    public EncadrantController(IEncadrantService iEncadrantService) {
        this.iEncadrantService = iEncadrantService;
    }

    @PostMapping(path = "/add-encadrant")
    public ResponseEntity<?> addEncadrant (@RequestBody EncadrantDto encadrantDto) throws EncadrantNotFoundException, EncadrantAlreadyExistException{
        this.iEncadrantService.addEncadrant(encadrantDto);
        return ResponseEntity.ok(encadrantDto);
    }
    @PutMapping(path = "/update/{encadrantId}")
    public ResponseEntity<?> updateEncadrant(@PathVariable long encadrantId, @RequestBody EncadrantDto encadrantDto)
        throws EncadrantNotFoundException {
        this.iEncadrantService.updateEncadrant(encadrantId, encadrantDto);
        return ResponseEntity.ok(encadrantDto);
    }
    @DeleteMapping(path = "/delete/{encadrantId}")
    public ResponseEntity<Void> deleteEncadrant(@PathVariable long encadrantId)
            throws EncadrantNotFoundException {
        this.iEncadrantService.deleteEncadrant(encadrantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping(path = "/{encadrantId}")
    public ResponseEntity<EncadrantDto> findEncadrantById(@PathVariable long encadrantId)
            throws EncadrantNotFoundException{
        Encadrant encadrant = this.iEncadrantService.findEncadrantByMatricule(encadrantId);
        EncadrantDto result = this.iEncadrantService.encadrantToEncadrantDto(encadrant);
        return ResponseEntity.ok(result);
    }
    @GetMapping(path = "/all")
    public ResponseEntity<List<EncadrantDto>> getAll(){
        List<EncadrantDto> all = this.iEncadrantService.getAll();
        return ResponseEntity.ok(all);
    }
}
