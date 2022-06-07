package org.astashonok.battleservice.factories.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.astashonok.battleservice.constants.BattleConstants;
import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.exceptions.BattleCreationFormException;
import org.astashonok.battleservice.factories.BattleFactory;
import org.astashonok.battleservice.models.BattleCreationForm;
import org.astashonok.battleservice.models.BattleStatus;
import org.astashonok.battleservice.utils.SecurityUtils;
import org.astashonok.battleservice.utils.ValidationUtils;
import org.astashonok.battleservice.validators.Validator;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BattleFactoryImpl implements BattleFactory {

    private final Validator<BattleCreationForm> validator;

    @Override
    public Battle create(@NonNull BattleCreationForm form) {
        ValidationUtils.asserts(form, validator::validate, BattleCreationFormException::new);

        return createBattle(
                SecurityUtils.getCurrentUserId(),
                form.getBoardHeight(),
                form.getBoardWidth(),
                form.getWinningNumberInRow()
        );
    }

    @Override
    public Battle crateDefault() {
        return createBattle(
                SecurityUtils.getCurrentUserId(),
                BattleConstants.GAME_BOARD_HEIGHT_DEFAULT,
                BattleConstants.GAME_BOARD_WIDTH_DEFAULT,
                BattleConstants.WINNING_NUMBER_IN_ROW_DEFAULT
        );
    }

    private Battle createBattle(UUID xParticipantId, int boardHeight, int boardWidth, int winningNumberInRow) {
        Battle battle = new Battle();
        battle.setXParticipantId(xParticipantId);
        battle.setBoardHeight(boardHeight);
        battle.setBoardWidth(boardWidth);
        battle.setWinningNumberInRow(winningNumberInRow);
        battle.setRemainingFreeMoveCount(boardHeight * boardWidth);
        battle.setStatus(BattleStatus.OPENED);
        battle.setBoard(getInitializedBoard(boardHeight, boardWidth));
        return battle;
    }

    private int[][] getInitializedBoard(int boardHeight, int boardWidth) {
        int[][] board = new int[boardHeight][boardWidth];
        Arrays.stream(board).forEach(array -> Arrays.fill(array, BattleConstants.GAME_BOARD_EMPTY_FIELD_NUMBER_VALUE));
        return board;
    }
}
