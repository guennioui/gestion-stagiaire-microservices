package ma.emsi.stagemicroservice.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class StageDto {
    private Long stageId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}
