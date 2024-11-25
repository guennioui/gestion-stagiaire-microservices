package ma.emsi.stagemicroservice.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class StageDto {
    private Long id;
    private Long stageId;
    private String stagiaireId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private StagiaireDto stagiaire;
}
