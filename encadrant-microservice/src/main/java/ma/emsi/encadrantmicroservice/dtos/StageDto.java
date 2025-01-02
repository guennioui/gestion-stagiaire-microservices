package ma.emsi.encadrantmicroservice.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class StageDto {
    private Long id;
    private Long stageId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String codeDepartement;
    private DepartementDto departementDto;
    private String matriculeEncadrant;
    private EncadrantDto encadrantDto;
}
