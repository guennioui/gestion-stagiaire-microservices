package ma.emsi.stagemicroservice.mappers;

import ma.emsi.stagemicroservice.dtos.StageDto;
import ma.emsi.stagemicroservice.entities.Stage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StageMapper {
    StageMapper INSTANCE = Mappers.getMapper(StageMapper.class);
    @Mapping(source = "id", target = "id")
    Stage stageDtoToStage(StageDto stageDto);
    @Mapping(source = "id", target = "id")
    StageDto stageToStageDto(Stage stage);
}
