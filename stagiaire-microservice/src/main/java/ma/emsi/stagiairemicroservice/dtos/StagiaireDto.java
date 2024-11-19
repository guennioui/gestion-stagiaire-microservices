package ma.emsi.stagiairemicroservice.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter @Setter @ToString
public class StagiaireDto {
    private String matricule;
    private String lastName;
    private String firstName;
    private LocalDate dateOfBirth;
}
