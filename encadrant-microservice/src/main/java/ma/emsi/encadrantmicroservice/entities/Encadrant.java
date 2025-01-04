package ma.emsi.encadrantmicroservice.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.emsi.encadrantmicroservice.dtos.StageDto;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Encadrant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String matricule;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String specialite;
}
