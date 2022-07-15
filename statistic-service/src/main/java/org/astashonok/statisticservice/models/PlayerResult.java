package org.astashonok.statisticservice.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.astashonok.statisticservice.constants.StatisticConstants;

@RequiredArgsConstructor
@Getter
public enum PlayerResult {

    VICTORY(StatisticConstants.PLAYER_RESULT_VICTORY),
    LOSING(StatisticConstants.PLAYER_RESULT_LOSING),
    DAW(StatisticConstants.PLAYER_RESULT_DAW);

    private final String result;
}
