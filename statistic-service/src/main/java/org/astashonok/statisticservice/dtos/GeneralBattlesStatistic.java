package org.astashonok.statisticservice.dtos;

import lombok.Data;

import java.time.Duration;

@Data
public class GeneralBattlesStatistic {

    private int maxStepsNumberInBattle;

    private int averageStepsNumberToWin;

    private Duration averageBattleDuration;

    private Duration averageStepDuration;
}
