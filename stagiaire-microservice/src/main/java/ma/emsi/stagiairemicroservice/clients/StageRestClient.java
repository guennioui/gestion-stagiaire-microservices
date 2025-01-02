package ma.emsi.stagiairemicroservice.clients;


import ma.emsi.stagiairemicroservice.dtos.StageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "stage-service", url = "http://localhost:8081/api/stage")
public interface StageRestClient {

    @PostMapping(path = "/add-stage")
    public ResponseEntity<?> addStage(@RequestBody StageDto stageDto);

    @PutMapping(path ="/update/{stageId}")
    public ResponseEntity<?> updateStage(@PathVariable long stageId, @RequestBody StageDto stageDto);

    @DeleteMapping(path = "/delete/{stageId}")
    public ResponseEntity<Void> deleteStage(@PathVariable long stageId);

    @GetMapping(path = "/{stageId}")
    public ResponseEntity<StageDto> findStageById(@PathVariable long stageId);

    @GetMapping(path = "/get-all")
    public ResponseEntity<Map<String, Object>> getAll();
}
