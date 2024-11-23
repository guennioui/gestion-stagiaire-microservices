package ma.emsi.stagemicroservice.mappers;

import ma.emsi.stagemicroservice.dtos.StageDto;
import ma.emsi.stagemicroservice.entities.Stage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StageMapper {
    StageMapper INSTANCE = Mappers.getMapper(StageMapper.class);
    Stage stageDtoToStage(StageDto stageDto);
    StageDto stageToStageDto(Stage stage);
}
