package org.astashonok.battleservice.models.errormessages;

import org.astashonok.battleservice.constants.ErrorMessagePattern;
import org.astashonok.validatingstarter.models.errormessages.impl.AbstractErrorMessageBasedOnPatternHolder;

public class CoordinatesOfGameBoardErrorMessageHolder extends AbstractErrorMessageBasedOnPatternHolder {

    public CoordinatesOfGameBoardErrorMessageHolder(int xCoordinate, int yCoordinate) {
        super(ErrorMessagePattern.COORDINATES_OF_GAME_BOARD, xCoordinate, yCoordinate);
    }
}
