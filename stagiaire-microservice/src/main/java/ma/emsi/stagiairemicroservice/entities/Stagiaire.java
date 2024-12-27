package ma.emsi.stagiairemicroservice.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.emsi.stagiairemicroservice.dtos.StageDto;

@Entity
@Setter @Getter @ToString
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Stagiaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String matricule;
    private String lastName;
    private String firstName;
    private String email;
    private String phoneNumber;
    private String schoolName;
    private Long stageId;
    @Transient
    private StageDto stageDto;
}
