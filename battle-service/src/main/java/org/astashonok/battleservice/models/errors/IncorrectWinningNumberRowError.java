package org.astashonok.battleservice.models.errors;

import org.astashonok.battleservice.constants.ErrorMessagePattern;
import org.astashonok.validatingstarter.models.validationerrors.impl.AbstractErrorMessageBasedOnPatternHolder;

public class IncorrectWinningNumberRowError extends AbstractErrorMessageBasedOnPatternHolder {

    public IncorrectWinningNumberRowError(int winningNumberInRow, int boardWidth, int boardHeight) {
        super(ErrorMessagePattern.INCORRECT_WINNING_NUMBER_ROW, winningNumberInRow, boardWidth, boardHeight);
    }
}
