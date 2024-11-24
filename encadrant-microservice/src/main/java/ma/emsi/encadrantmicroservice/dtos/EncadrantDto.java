package ma.emsi.encadrantmicroservice.dtos;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EncadrantDto {
    private Long matricule;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String specialite;
}
