package org.astashonok.statisticservice.rest;

import lombok.RequiredArgsConstructor;
import org.astashonok.statisticservice.dtos.BoardSize;
import org.astashonok.statisticservice.dtos.GeneralBattlesStatistic;
import org.astashonok.statisticservice.services.BattleStatisticService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;

@RestController("/statistic")
@RequiredArgsConstructor
public class BattleStatisticController {

    private final BattleStatisticService battleStatisticService;

    @GetMapping("/top-popular-board-sizes")
    public List<BoardSize> getTopPopularBoardSizes(@RequestParam int topNumber) {
        return battleStatisticService.getTopPopularBoardSizes(topNumber);
    }

    @GetMapping("/max-steps-number-in-battle")
    public int getMaxStepsNumberInBattle() {
        return battleStatisticService.getMaxStepsNumberInBattle();
    }

    @GetMapping("/average-steps-number-to-win")
    public int getAverageStepsNumberToWin() {
        return battleStatisticService.getAverageStepsNumberToWin();
    }

    @GetMapping("/average-battle-duration")
    public Duration getAverageBattleDuration() {
        return battleStatisticService.getAverageBattleDuration();
    }

    @GetMapping("/average-step-duration")
    public Duration getAverageStepDuration() {
        return battleStatisticService.getAverageStepDuration();
    }

    @GetMapping("/general-battles-statistic")
    public GeneralBattlesStatistic getGeneralBattlesStatistic() {
        return battleStatisticService.getGeneralBattlesStatistic();
    }
}
