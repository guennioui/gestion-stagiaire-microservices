package ma.emsi.stagemicroservice.repositories;

import ma.emsi.stagemicroservice.dtos.StageDto;
import ma.emsi.stagemicroservice.entities.Stage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StageRepository extends JpaRepository<Stage, Long> {
    Optional<Stage> findByStageId(Long stageId);
    Page<Stage> findAll(Pageable pageable);
}
