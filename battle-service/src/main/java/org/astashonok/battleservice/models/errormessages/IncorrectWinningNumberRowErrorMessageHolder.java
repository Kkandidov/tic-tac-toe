package org.astashonok.battleservice.models.errormessages;

import org.astashonok.battleservice.constants.ErrorMessagePattern;
import org.astashonok.validatingstarter.models.errormessages.impl.AbstractErrorMessageBasedOnPatternHolder;

public class IncorrectWinningNumberRowErrorMessageHolder extends AbstractErrorMessageBasedOnPatternHolder {

    public IncorrectWinningNumberRowErrorMessageHolder(int winningNumberInRow, int boardWidth, int boardHeight) {
        super(ErrorMessagePattern.INCORRECT_WINNING_NUMBER_ROW, winningNumberInRow, boardWidth, boardHeight);
    }
}
