package org.astashonok.battleservice.validators.impl;

import lombok.NonNull;
import org.astashonok.battleservice.constants.BattleConstants;
import org.astashonok.battleservice.constants.ErrorMessage;
import org.astashonok.battleservice.models.BattleCreationForm;
import org.astashonok.battleservice.validators.BattleCreationFormValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Component
public class BattleCreationFormValidatorImpl implements BattleCreationFormValidator {

    @Override
    public List<String> validate(@NonNull BattleCreationForm form) {
        List<String> errorMessages = new ArrayList<>();

        int boardWidth = form.getBoardWidth();
        int boardHeight = form.getBoardHeight();
        int winningNumberInRow = form.getWinningNumberInRow();

        addMessageIfTrue(errorMessages,
                String.format(ErrorMessage.INCORRECT_GAME_BOARD_HEIGHT, boardHeight),
                () -> boardHeight < BattleConstants.GAME_BOARD_HEIGHT_DEFAULT);

        addMessageIfTrue(errorMessages,
                String.format(ErrorMessage.INCORRECT_GAME_BOARD_WIDTH, boardWidth),
                () -> boardWidth < BattleConstants.GAME_BOARD_WIDTH_DEFAULT);

        addMessageIfTrue(errorMessages,
                String.format(ErrorMessage.INCORRECT_WINNING_NUMBER_ROW, winningNumberInRow, boardWidth, boardHeight),
                () -> winningNumberInRow <= boardWidth && winningNumberInRow <= boardHeight);

        return errorMessages;
    }

    void addMessageIfTrue(List<String> exceptionMessages, String message, Supplier<Boolean> isTrue) {
        if (Boolean.TRUE.equals(isTrue.get())) {
            exceptionMessages.add(message);
        }
    }
}
