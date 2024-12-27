package ma.emsi.stagiairemicroservice.dtos;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EncadrantDto {
    private String matricule;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String specialite;
}