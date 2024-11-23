package ma.emsi.stagiairemicroservice.controllers;

import ma.emsi.stagiairemicroservice.dtos.StagiaireDto;
import ma.emsi.stagiairemicroservice.entities.Stagiaire;
import ma.emsi.stagiairemicroservice.errors.ErrorResponse;
import ma.emsi.stagiairemicroservice.exceptions.StagiaireAlreadyExistException;
import ma.emsi.stagiairemicroservice.exceptions.StagiaireNotFoundException;
import ma.emsi.stagiairemicroservice.services.IService.IStagiaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/stagiaire")
public class StagiaireController {
    private final IStagiaireService stagiaireService;

    @Autowired
    public StagiaireController(IStagiaireService stagiaireService) {
        this.stagiaireService = stagiaireService;
    }

    @PostMapping(path = "/add-stagiaire")
    public ResponseEntity<StagiaireDto> addNewStagiaire(@RequestBody StagiaireDto stagiaireDto) throws
            StagiaireAlreadyExistException {
        Stagiaire optionalStagiaire = null;
        try{
            optionalStagiaire = this.stagiaireService.findByMatricule(stagiaireDto.getMatricule());
        }catch(StagiaireNotFoundException exception){
            System.out.println(exception.getMessage());
        }
        if(optionalStagiaire != null){
            throw new StagiaireAlreadyExistException("le stagiaire que vous tentez de l'ajouté existe déja");
        }
        this.stagiaireService.addStagiaire(
         this.stagiaireService.stagiaireDTOToStagiaire(stagiaireDto)
        );
        return ResponseEntity.ok(stagiaireDto);
    }

    @DeleteMapping(path = "/delete-stagiaire/{matricule}")
    public ResponseEntity<String> removeStagiaire(@PathVariable String matricule) throws StagiaireNotFoundException {
        this.stagiaireService.removeStagiaire(matricule);
        return ResponseEntity.ok("le stagiaire a été supprimer avec succés");
    }

    @PutMapping(path = "/update-stagiaire/{matricule}")
    public ResponseEntity<StagiaireDto> updateStagiaire(@PathVariable String matricule, @RequestBody StagiaireDto stagiaireDto) throws StagiaireNotFoundException {
        this.stagiaireService.updateStagiaire(matricule, stagiaireDto);
        return ResponseEntity.ok(stagiaireDto);
    }

    @GetMapping(path = "/{matricule}")
    public ResponseEntity<StagiaireDto> findStagiaireByMatricule(@PathVariable String matricule) throws StagiaireNotFoundException {
        Stagiaire findByMatricule = this.stagiaireService.findByMatricule(matricule);
        StagiaireDto result = this.stagiaireService.stagiaireToStagiaireDTO(findByMatricule);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/get-all")
    public ResponseEntity<List<StagiaireDto>> findAllStagiaire(){
        return ResponseEntity.ok(this.stagiaireService.getAll());
    }

    @ExceptionHandler(value = StagiaireNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // is used to control the specific HTTP status code returned for a particular exception. 409
    public ErrorResponse HandleStagiaireNotFoundException(StagiaireNotFoundException ex){
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }
}
