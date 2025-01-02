package ma.emsi.stagemicroservice.clients;

import ma.emsi.stagemicroservice.dtos.DepartementDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "departement-service", url = "http://localhost:8082/api/departement")
public interface DepartementRestClient {

    @PostMapping(path = "/add-departement")
    public ResponseEntity<?> addDepartement (@RequestBody DepartementDto departementDto);

    @PutMapping(path = "/update/{code}")
    public ResponseEntity<?> updateDepartement(@PathVariable String code, @RequestBody DepartementDto departementDto);

    @DeleteMapping(path = "/delete/{code}")
    public ResponseEntity<Void> deleteDepartement(@PathVariable String code);

    @GetMapping(path = "/{code}")
    public ResponseEntity<DepartementDto> findDepartementByCode(@PathVariable String code);

    @GetMapping(path = "/get-all")
    public ResponseEntity<Map<String, Object>> getAll();
}
