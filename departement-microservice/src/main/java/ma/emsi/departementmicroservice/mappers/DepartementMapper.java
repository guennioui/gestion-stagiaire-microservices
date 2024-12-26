package ma.emsi.departementmicroservice.mappers;

import ma.emsi.departementmicroservice.dtos.DepartementDto;
import ma.emsi.departementmicroservice.entities.Departement;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DepartementMapper {
    DepartementMapper INSTANCE = Mappers.getMapper(DepartementMapper.class);

    Departement DepartementDtoToDepartment(DepartementDto departementDto);
    DepartementDto DepartementToDepartmentDto(Departement departement);
}
