package org.astashonok.battleservice.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.astashonok.battleservice.constants.BattleConstants;
import org.astashonok.battleservice.constants.ErrorMessage;
import org.astashonok.battleservice.constraints.annotations.BattleCreationFormValidated;

import javax.validation.constraints.Min;

@Getter
@RequiredArgsConstructor
@BattleCreationFormValidated
public class BattleCreationForm {

    @Min(value = BattleConstants.GAME_BOARD_HEIGHT_DEFAULT, message = ErrorMessage.INCORRECT_GAME_BOARD_HEIGHT)
    private final int boardHeight;

    @Min(value = BattleConstants.GAME_BOARD_WIDTH_DEFAULT, message = ErrorMessage.INCORRECT_GAME_BOARD_WIDTH)
    private final int boardWidth;
    private final int winningNumberInRow;
}
