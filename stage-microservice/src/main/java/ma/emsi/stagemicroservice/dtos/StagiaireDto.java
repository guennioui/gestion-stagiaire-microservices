package ma.emsi.stagemicroservice.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class StagiaireDto {
    private String matricule;
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;
    private String schoolName;
}
