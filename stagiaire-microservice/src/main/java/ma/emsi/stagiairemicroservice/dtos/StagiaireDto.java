package ma.emsi.stagiairemicroservice.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class StagiaireDto {
    private String matricule;
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;
    private String schoolName;
    private StageDto stageDto;
}
