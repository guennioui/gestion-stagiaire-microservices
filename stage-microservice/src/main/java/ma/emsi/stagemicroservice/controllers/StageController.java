package ma.emsi.stagemicroservice.controllers;

import ma.emsi.stagemicroservice.dtos.StageDto;
import ma.emsi.stagemicroservice.exceptions.StageAlreadyExistingException;
import ma.emsi.stagemicroservice.exceptions.StageNotFoundException;
import ma.emsi.stagemicroservice.entities.Stage;
import ma.emsi.stagemicroservice.services.Iservice.IStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/stage")
@CrossOrigin("*")
public class StageController {
    private final IStageService stageService;
    @Autowired
    public StageController(IStageService iStageService) {
        this.stageService = iStageService;
    }

    @PostMapping(path = "/add-stage")
    public ResponseEntity<?> addStage(@RequestBody StageDto stageDto) throws StageAlreadyExistingException, StageNotFoundException {
        this.stageService.addStage(stageDto);
        return ResponseEntity.ok(stageDto);
    }

    @PutMapping(path ="/update/{stageId}")
    public ResponseEntity<?> updateStage(@PathVariable long stageId, @RequestBody StageDto stageDto)
    throws StageNotFoundException {
        this.stageService.updateStage(stageId, stageDto);
        return ResponseEntity.ok(stageDto);
    }

    @DeleteMapping(path = "/delete/{stageId}")
    public ResponseEntity<Void> deleteStage(@PathVariable long stageId)
        throws StageNotFoundException{
        this.stageService.deleteStage(stageId);
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

    @GetMapping(path = "/{stageId}")
    public ResponseEntity<?> findStageById(@PathVariable long stageId)
        throws StageNotFoundException{
        StageDto stageById = this.stageService.findStageById(stageId);
        return ResponseEntity.ok(stageById);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<StageDto>> getAll(){
        List<StageDto> all = this.stageService.getAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping(path = "/get-all")
    public ResponseEntity<Map<String, Object>> findAllStage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Page<StageDto> stagePage = stageService.getAll(page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("content", stagePage.getContent());
        response.put("currentPage", stagePage.getNumber());
        response.put("totalItems", stagePage.getTotalElements());
        response.put("totalPages", stagePage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/assign-departement-To-stage/{stageId}/{codeDepartement}")
    public ResponseEntity<?> assignDepartementToStage(@PathVariable  Long stageId, @PathVariable String codeDepartement)
            throws StageNotFoundException{
        this.stageService.assignDepartementToStage(stageId, codeDepartement);
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

    @PostMapping(path = "/assign-encadrant-To-stage/{stageId}/{matriculeEncadrant}")
    public ResponseEntity<?> assignEncadrantToStage(@PathVariable Long stageId, @PathVariable String matriculeEncadrant)
            throws StageNotFoundException{
        this.stageService.assignEncadrantToStage(stageId, matriculeEncadrant);
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }
}
