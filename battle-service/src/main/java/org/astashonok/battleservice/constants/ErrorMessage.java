package org.astashonok.battleservice.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ErrorMessage {
    public static final String BATTLE_NOT_EXISTS = "There is no battle for the given id: %s";
    public static final String COORDINATE_NOT_POSITIVE = "Passed '%s' (%s) coordinate is not positive";
    public static final String COORDINATES_OF_GAME_BOARD = "Passed coordinates ('x' = %s, 'y' = %s) off the game board";
    public static final String MOVE_NOT_FOR_CURRENT_USER = "Queue to walk not the current user";
    public static final String BATTLE_STATUS_IS_OPENED = "There is no rival for the passed battle";
    public static final String BATTLE_IS_FINISHED = "Battle is finished or there are no more free moves on the game board";
    public static final String INCORRECT_GAME_BOARD_HEIGHT = "Passed game board height ('%s') less than default game board height = '" + BattleConstants.GAME_BOARD_HEIGHT_DEFAULT + "'";
    public static final String INCORRECT_GAME_BOARD_WIDTH = "Passed game board width ('%s') less than default game board width = '" + BattleConstants.GAME_BOARD_WIDTH_DEFAULT + "'";
    public static final String INCORRECT_WINNING_NUMBER_ROW = "Passed winning number row ('%s') must be more or equals game board width ('%s') and game board height ('%s')";
}
