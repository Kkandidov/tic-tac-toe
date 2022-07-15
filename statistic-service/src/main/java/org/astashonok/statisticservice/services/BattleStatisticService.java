package org.astashonok.statisticservice.services;

import org.astashonok.statisticservice.dtos.BoardSize;
import org.astashonok.statisticservice.dtos.GeneralBattlesStatistic;

import java.time.Duration;
import java.util.List;

public interface BattleStatisticService {
    List<BoardSize> getTopPopularBoardSizes(int topNumber);

    int getMaxStepsNumberInBattle();

    int getAverageStepsNumberToWin();

    Duration getAverageBattleDuration();

    Duration getAverageStepDuration();

    GeneralBattlesStatistic getGeneralBattlesStatistic();
}
