package org.astashonok.battleservice.models;

import lombok.Data;

@Data
public class BattleCreationForm {

    private final int boardHeight;
    private final int boardWidth;
    private final int winningNumberInRow;
}
