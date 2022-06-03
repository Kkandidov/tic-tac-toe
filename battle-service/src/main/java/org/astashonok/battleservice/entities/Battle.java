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
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "battles")
@TypeDef(name = "board_array", typeClass = DoubleArrayType.class)
@Getter
@Setter
@NoArgsConstructor
public class Battle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int boardHeight;
    private int boardWidth;

    @Type(type = "board_array", parameters = @org.hibernate.annotations.Parameter(name = "sql_array_type", value = "integer"))
    @Column(columnDefinition = "integer[][]")
    private int[][] board;

    private int winningNumberInRow;

    @Enumerated(EnumType.STRING)
    private BattleStatus status;
    private Long xParticipantId;
    private Long oParticipantId;
    private Long nextMoveParticipantId;
    private int remainingFreeMoveCount;

    private Long winnerId;

    public Battle(Long xParticipantId) {
        this(xParticipantId, BattleConstants.GAME_BOARD_HEIGHT_DEFAULT, BattleConstants.GAME_BOARD_WIDTH_DEFAULT,
                BattleConstants.WINNING_NUMBER_IN_ROW_DEFAULT);
    }

    public Battle(Long xParticipantId, int boardHeight, int boardWidth, int winningNumberInRow) {
        this.xParticipantId = xParticipantId;
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        this.winningNumberInRow = winningNumberInRow;
        this.remainingFreeMoveCount = boardHeight * boardWidth;
        this.status = BattleStatus.OPENED;
        initBoard();
    }

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

    public void setFieldNumberValue(Long userId, int xCoordinate, int yCoordinate) {
        if (Objects.equals(userId, xParticipantId)) {
            board[yCoordinate][xCoordinate] = getXNumberValue();
        } else {
            board[yCoordinate][xCoordinate] = getONumberValue();
        }
    }

    private void initBoard() {
        board = new int[boardHeight][boardWidth];
        Arrays.stream(board).forEach(array -> Arrays.fill(array, BattleConstants.GAME_BOARD_EMPTY_FIELD_NUMBER_VALUE));
    }
}
