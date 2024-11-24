package ma.emsi.encadrantmicroservice.mappers;

import ma.emsi.encadrantmicroservice.dtos.EncadrantDto;
import ma.emsi.encadrantmicroservice.entities.Encadrant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface EncadrantMapper {
    EncadrantMapper INSTANCE = Mappers.getMapper(EncadrantMapper.class);
    @Mapping(source = "matricule", target = "matricule")
    Encadrant encadrantDtoToEncadrant(EncadrantDto encadrantDto);
    EncadrantDto encadrantToEncadrantDto(Encadrant encadrant);
}
