package ma.emsi.stagiairemicroservice.repositories;

import ma.emsi.stagiairemicroservice.entities.Stagiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StagiaireRepository extends JpaRepository<Stagiaire, Long> {
    Optional<Stagiaire> findByMatricule(String matricule);
    List<Stagiaire> findAllByStageId(Long stageId);
}
