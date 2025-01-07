package ma.emsi.stagemicroservice.mappers;

import javax.annotation.processing.Generated;
import ma.emsi.stagemicroservice.dtos.StageDto;
import ma.emsi.stagemicroservice.entities.Stage;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-07T01:47:58+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
public class StageMapperImpl implements StageMapper {

    @Override
    public Stage stageDtoToStage(StageDto stageDto) {
        if ( stageDto == null ) {
            return null;
        }

        Stage stage = new Stage();

        stage.setId( stageDto.getId() );
        stage.setStageId( stageDto.getStageId() );
        stage.setTitle( stageDto.getTitle() );
        stage.setDescription( stageDto.getDescription() );
        stage.setStartDate( stageDto.getStartDate() );
        stage.setEndDate( stageDto.getEndDate() );
        stage.setCodeDepartement( stageDto.getCodeDepartement() );
        stage.setMatriculeEncadrant( stageDto.getMatriculeEncadrant() );
        stage.setDepartementDto( stageDto.getDepartementDto() );
        stage.setEncadrantDto( stageDto.getEncadrantDto() );

        return stage;
    }

    @Override
    public StageDto stageToStageDto(Stage stage) {
        if ( stage == null ) {
            return null;
        }

        StageDto stageDto = new StageDto();

        stageDto.setId( stage.getId() );
        stageDto.setStageId( stage.getStageId() );
        stageDto.setTitle( stage.getTitle() );
        stageDto.setDescription( stage.getDescription() );
        stageDto.setStartDate( stage.getStartDate() );
        stageDto.setEndDate( stage.getEndDate() );
        stageDto.setCodeDepartement( stage.getCodeDepartement() );
        stageDto.setDepartementDto( stage.getDepartementDto() );
        stageDto.setMatriculeEncadrant( stage.getMatriculeEncadrant() );
        stageDto.setEncadrantDto( stage.getEncadrantDto() );

        return stageDto;
    }
}
