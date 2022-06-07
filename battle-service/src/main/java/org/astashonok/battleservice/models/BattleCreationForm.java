package org.astashonok.battleservice.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BattleCreationForm {

    private final int boardHeight;
    private final int boardWidth;
    private final int winningNumberInRow;
}
