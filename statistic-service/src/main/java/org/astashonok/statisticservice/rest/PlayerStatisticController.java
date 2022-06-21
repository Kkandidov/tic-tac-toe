package org.astashonok.statisticservice.rest;

import lombok.RequiredArgsConstructor;
import org.astashonok.statisticservice.dtos.PlayerBattlesStatistic;
import org.astashonok.statisticservice.dtos.PlayerStatistic;
import org.astashonok.statisticservice.services.PlayerStatisticService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.UUID;

@RestController("/statistic")
@RequiredArgsConstructor
@Validated
public class PlayerStatisticController {

    private final PlayerStatisticService playerStatisticService;

    @GetMapping("/players/{playerId}")
    public PlayerBattlesStatistic getBattlesStatistic(@PathVariable UUID playerId) {
        return playerStatisticService.getBattlesStatistic(playerId);
    }

    @GetMapping("/players")
    public List<PlayerStatistic> getPlayersStatistic() {
        return playerStatisticService.getPlayersStatistic();
    }

    @GetMapping("/top-players")
    public List<PlayerStatistic> getTopPlayersStatistic(@RequestParam int topNumber) {
        return playerStatisticService.getTopPlayersStatistic(topNumber);
    }
}
