package ma.emsi.stagemicroservice.clients;

import ma.emsi.stagemicroservice.dtos.StagiaireDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "stagiaire-service")
public interface StagiaireRestClient {

    @PostMapping(path = "/api/stagiaire/add-stagiaire")
    public ResponseEntity<StagiaireDto> addNewStagiaire(@RequestBody StagiaireDto stagiaire);

    @DeleteMapping(path = "/api/stagiaire/delete-stagiaire/{matricule}")
    public ResponseEntity<String> removeStagiaire(@PathVariable String matricule);

    @PutMapping(path = "/api/stagiaire/update-stagiaire/{matricule}")
    public ResponseEntity<StagiaireDto> updateStagiaire(@PathVariable String matricule, @RequestBody StagiaireDto stagiaire);

    @GetMapping(path = "/api/stagiaire/{matricule}")
    public ResponseEntity<StagiaireDto> findStagiaireByMatricule(@PathVariable String matricule);

    @GetMapping(path = "/api/stagiaire/get-all")
    public ResponseEntity<List<StagiaireDto>> findAllStagiaire();
}
