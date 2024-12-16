package ma.emsi.stagemicroservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.emsi.stagemicroservice.dtos.StagiaireDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor@NoArgsConstructor
public class Stage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long stageId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    @ElementCollection
    private List<String> stagiaireIds = new ArrayList<>();
}
