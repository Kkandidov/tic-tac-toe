package org.astashonok.battleservice.mappers.dtos;

import javax.annotation.processing.Generated;
import org.astashonok.battleservice.dtos.MoveDto;
import org.astashonok.battleservice.entities.Move;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-06-09T17:15:05+0300",
    comments = "version: 1.5.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.jar, environment: Java 11.0.15 (Amazon.com Inc.)"
)
@Component
public class MoveDtoMapperImpl implements MoveDtoMapper {

    @Override
    public MoveDto toDto(Move entity) {
        if ( entity == null ) {
            return null;
        }

        MoveDto moveDto = new MoveDto();

        moveDto.setId( entity.getId() );
        moveDto.setBattleId( entity.getBattleId() );
        moveDto.setUserId( entity.getUserId() );
        moveDto.setXCoordinate( entity.getXCoordinate() );
        moveDto.setYCoordinate( entity.getYCoordinate() );
        moveDto.setTime( entity.getTime() );

        return moveDto;
    }

    @Override
    public Move toEntity(MoveDto dto) {
        if ( dto == null ) {
            return null;
        }

        Move move = new Move();

        move.setId( dto.getId() );
        move.setBattleId( dto.getBattleId() );
        move.setUserId( dto.getUserId() );
        move.setXCoordinate( dto.getXCoordinate() );
        move.setYCoordinate( dto.getYCoordinate() );
        move.setTime( dto.getTime() );

        return move;
    }
}
