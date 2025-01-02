package ma.emsi.encadrantmicroservice.dtos;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DepartementDto {
    private Long id;
    private String name;
    private String code;
    private String description;
}
