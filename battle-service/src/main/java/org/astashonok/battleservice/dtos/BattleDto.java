package org.astashonok.battleservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.astashonok.battleservice.models.BattleStatus;

import java.util.UUID;

@Getter
@Setter
public class BattleDto {

    private UUID id;
    private int boardHeight;
    private int boardWidth;
    private int winningNumberInRow;
    private BattleStatus status;
    private UUID xParticipantId;
    private UUID oParticipantId;
    private UUID winnerId;
}
