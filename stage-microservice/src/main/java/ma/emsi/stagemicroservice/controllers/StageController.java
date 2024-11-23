package ma.emsi.stagemicroservice.controllers;

import ma.emsi.stagemicroservice.dtos.StageDto;
import ma.emsi.stagemicroservice.exceptions.StageAlreadyExistingException;
import ma.emsi.stagemicroservice.exceptions.StageNotFoundException;
import ma.emsi.stagemicroservice.entities.Stage;
import ma.emsi.stagemicroservice.services.Iservice.IStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/stage")
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
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping(path = "/{stageId}")
    public ResponseEntity<StageDto> findStageById(@PathVariable long stageId)
        throws StageNotFoundException{
        Stage stage = this.stageService.findStageById(stageId);
        StageDto result = this.stageService.stageToStageDto(stage);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<StageDto>> getAll(){
        List<StageDto> all = this.stageService.getAll();
        return ResponseEntity.ok(all);
    }

}
