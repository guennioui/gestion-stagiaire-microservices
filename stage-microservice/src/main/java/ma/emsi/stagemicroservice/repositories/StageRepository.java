package ma.emsi.stagemicroservice.repositories;

import ma.emsi.stagemicroservice.entities.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageRepository extends JpaRepository<Stage, Long> {
}
