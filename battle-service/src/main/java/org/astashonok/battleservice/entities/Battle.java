package org.astashonok.battleservice.entities;

import com.vladmihalcea.hibernate.type.array.DoubleArrayType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.astashonok.battleservice.constants.BattleConstants;
import org.astashonok.battleservice.models.BattleStatus;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "battles")
@TypeDef(name = "board_array", typeClass = DoubleArrayType.class)
@Getter
@Setter
@NoArgsConstructor
public class Battle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID id;

    private int boardHeight;
    private int boardWidth;

    @Type(type = "board_array", parameters = @org.hibernate.annotations.Parameter(name = "sql_array_type", value = "integer"))
    @Column(columnDefinition = "integer[][]")
    private int[][] board;

    private int winningNumberInRow;

    @Enumerated(EnumType.STRING)
    private BattleStatus status;

    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID xParticipantId;

    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID oParticipantId;

    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID nextMoveParticipantId;
    private int remainingFreeMoveCount;

    @Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID winnerId;

    public void decrementRemainingFreeMoveCount() {
        remainingFreeMoveCount--;
    }

    public void swapNextMoveParticipantId() {
        nextMoveParticipantId = Objects.equals(nextMoveParticipantId, xParticipantId) ? oParticipantId : xParticipantId;
    }

    public int getXNumberValue() {
        return BattleConstants.GAME_BOARD_X_FIELD_NUMBER_VALUE;
    }

    public int getONumberValue() {
        return winningNumberInRow * BattleConstants.GAME_BOARD_X_FIELD_NUMBER_VALUE + 1;
    }

    public void setFieldNumberValue(UUID userId, int xCoordinate, int yCoordinate) {
        if (Objects.equals(userId, xParticipantId)) {
            board[yCoordinate][xCoordinate] = getXNumberValue();
        } else {
            board[yCoordinate][xCoordinate] = getONumberValue();
        }
    }
}
