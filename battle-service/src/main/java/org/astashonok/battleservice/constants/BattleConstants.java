package org.astashonok.battleservice.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BattleConstants {

    public static final int GAME_BOARD_HEIGHT_DEFAULT = 3;
    public static final int GAME_BOARD_WIDTH_DEFAULT = 3;
    public static final int WINNING_NUMBER_IN_ROW_DEFAULT = 3;
    public static final int GAME_BOARD_EMPTY_FIELD_NUMBER_VALUE = 0;
    public static final int GAME_BOARD_X_FIELD_NUMBER_VALUE = 1;
    public static final String X_NAME = "x";
    public static final String Y_NAME = "y";
}
