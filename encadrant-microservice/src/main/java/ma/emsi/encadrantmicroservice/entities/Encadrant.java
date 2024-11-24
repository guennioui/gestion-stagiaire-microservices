package ma.emsi.encadrantmicroservice.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Encadrant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private Long matricule;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String specialite;
}
