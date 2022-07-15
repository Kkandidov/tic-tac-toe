package org.astashonok.statisticservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.astashonok.statisticservice.dtos.BoardSize;
import org.astashonok.statisticservice.dtos.GeneralBattlesStatistic;
import org.astashonok.statisticservice.services.BattleStatisticService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BattleStatisticServiceImpl implements BattleStatisticService {

    @Override
    public List<BoardSize> getTopPopularBoardSizes(int topNumber) {
        return null;
    }

    @Override
    public int getMaxStepsNumberInBattle() {
        return 0;
    }

    @Override
    public int getAverageStepsNumberToWin() {
        return 0;
    }

    @Override
    public Duration getAverageBattleDuration() {
        return null;
    }

    @Override
    public Duration getAverageStepDuration() {
        return null;
    }

    @Override
    public GeneralBattlesStatistic getGeneralBattlesStatistic() {
        return null;
    }
}
