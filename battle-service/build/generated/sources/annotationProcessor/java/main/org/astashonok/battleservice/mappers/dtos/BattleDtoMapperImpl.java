package org.astashonok.battleservice.mappers.dtos;

import java.util.Arrays;
import javax.annotation.processing.Generated;
import org.astashonok.battleservice.dtos.BattleDto;
import org.astashonok.battleservice.entities.Battle;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-06-09T17:15:05+0300",
    comments = "version: 1.5.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.jar, environment: Java 11.0.15 (Amazon.com Inc.)"
)
@Component
public class BattleDtoMapperImpl implements BattleDtoMapper {

    @Override
    public BattleDto toDto(Battle entity) {
        if ( entity == null ) {
            return null;
        }

        BattleDto battleDto = new BattleDto();

        battleDto.setId( entity.getId() );
        battleDto.setBoardHeight( entity.getBoardHeight() );
        battleDto.setBoardWidth( entity.getBoardWidth() );
        int[][] board = entity.getBoard();
        if ( board != null ) {
            battleDto.setBoard( Arrays.copyOf( board, board.length ) );
        }
        battleDto.setWinningNumberInRow( entity.getWinningNumberInRow() );
        battleDto.setStatus( entity.getStatus() );
        battleDto.setXParticipantId( entity.getXParticipantId() );
        battleDto.setOParticipantId( entity.getOParticipantId() );
        battleDto.setNextMoveParticipantId( entity.getNextMoveParticipantId() );
        battleDto.setRemainingFreeMoveCount( entity.getRemainingFreeMoveCount() );
        battleDto.setWinnerId( entity.getWinnerId() );

        return battleDto;
    }

    @Override
    public Battle toEntity(BattleDto dto) {
        if ( dto == null ) {
            return null;
        }

        Battle battle = new Battle();

        battle.setId( dto.getId() );
        battle.setBoardHeight( dto.getBoardHeight() );
        battle.setBoardWidth( dto.getBoardWidth() );
        int[][] board = dto.getBoard();
        if ( board != null ) {
            battle.setBoard( Arrays.copyOf( board, board.length ) );
        }
        battle.setWinningNumberInRow( dto.getWinningNumberInRow() );
        battle.setStatus( dto.getStatus() );
        battle.setXParticipantId( dto.getXParticipantId() );
        battle.setOParticipantId( dto.getOParticipantId() );
        battle.setNextMoveParticipantId( dto.getNextMoveParticipantId() );
        battle.setRemainingFreeMoveCount( dto.getRemainingFreeMoveCount() );
        battle.setWinnerId( dto.getWinnerId() );

        return battle;
    }
}
