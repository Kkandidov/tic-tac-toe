package org.astashonok.battleservice.models.errormessages;

import org.astashonok.battleservice.constants.ErrorMessagePattern;
import org.astashonok.validatingstarter.models.errormessages.impl.AbstractErrorMessageBasedOnPatternHolder;

public class CoordinatesOfGameBoardError extends AbstractErrorMessageBasedOnPatternHolder {

    public CoordinatesOfGameBoardError(int xCoordinate, int yCoordinate) {
        super(ErrorMessagePattern.COORDINATES_OF_GAME_BOARD, xCoordinate, yCoordinate);
    }
}
