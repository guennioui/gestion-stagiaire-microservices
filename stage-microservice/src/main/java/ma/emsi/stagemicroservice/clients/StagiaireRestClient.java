package ma.emsi.stagemicroservice.clients;

import ma.emsi.stagemicroservice.dtos.StagiaireDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "stagiaire-service", url = "http://localhost:8080/api/stagiaire")
public interface StagiaireRestClient {

    @PostMapping(path = "/add-stagiaire")
    public ResponseEntity<StagiaireDto> addNewStagiaire(@RequestBody StagiaireDto stagiaire);

    @DeleteMapping(path = "/delete-stagiaire/{matricule}")
    public ResponseEntity<String> removeStagiaire(@PathVariable String matricule);

    @PutMapping(path = "/update-stagiaire/{matricule}")
    public ResponseEntity<StagiaireDto> updateStagiaire(@PathVariable String matricule, @RequestBody StagiaireDto stagiaire);

    @GetMapping(path = "/{matricule}")
    public ResponseEntity<StagiaireDto> findStagiaireByMatricule(@PathVariable String matricule);

    @GetMapping(path = "/get-all")
    public ResponseEntity<List<StagiaireDto>> findAllStagiaire();
}
