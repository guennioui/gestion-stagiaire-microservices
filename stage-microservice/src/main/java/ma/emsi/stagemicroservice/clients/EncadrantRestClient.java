package ma.emsi.stagemicroservice.clients;

import ma.emsi.stagemicroservice.dtos.EncadrantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "encadrant-service", url = "http://localhost:8083/api/encadrant")
public interface EncadrantRestClient {

    @PostMapping(path = "/add-encadrant")
    public ResponseEntity<?> addEncadrant (@RequestBody EncadrantDto encadrantDto);

    @PutMapping(path = "/update/{encadrantId}")
    public ResponseEntity<?> updateEncadrant(@PathVariable long encadrantId, @RequestBody EncadrantDto encadrantDto);

    @DeleteMapping(path = "/delete/{encadrantId}")
    public ResponseEntity<Void> deleteEncadrant(@PathVariable long encadrantId);

    @GetMapping(path = "/find-by-matricule/{matricule}")
    public ResponseEntity<EncadrantDto> findEncadrantByMatricule(@PathVariable String matricule);

    @GetMapping(path = "/all")
    public ResponseEntity<List<EncadrantDto>> getAll();
}
