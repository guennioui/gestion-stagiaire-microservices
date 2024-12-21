package ma.emsi.stagiairemicroservice.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class StageDto {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}
