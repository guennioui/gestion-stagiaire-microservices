package ma.emsi.encadrantmicroservice.repositories;

import ma.emsi.encadrantmicroservice.entities.Encadrant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EncadrantRepository extends JpaRepository<Encadrant, Long> {

    Optional<Encadrant> findByMatricule(String matricule);
    Page<Encadrant> findAll(Pageable pageable);
}
