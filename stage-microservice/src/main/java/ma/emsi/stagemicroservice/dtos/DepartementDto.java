package ma.emsi.stagemicroservice.dtos;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DepartementDto {
    private String name;
    private String code;
    private String description;
}
