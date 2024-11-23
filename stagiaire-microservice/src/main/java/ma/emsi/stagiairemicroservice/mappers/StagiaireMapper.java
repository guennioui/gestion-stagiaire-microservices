package ma.emsi.stagiairemicroservice.mappers;


import ma.emsi.stagiairemicroservice.dtos.StagiaireDto;
import ma.emsi.stagiairemicroservice.entities.Stagiaire;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface StagiaireMapper {
    StagiaireMapper INSTANCE = Mappers.getMapper(StagiaireMapper.class);
    StagiaireDto stagiaireToStagiaireDTO(Stagiaire stagiaire);
    Stagiaire stagiaireDTOToStagiaire(StagiaireDto stagiaireDto);
}
