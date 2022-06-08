package org.astashonok.statisticservice.rest;

import lombok.RequiredArgsConstructor;
import org.astashonok.statisticservice.dtos.PlayerBattlesStatistic;
import org.astashonok.statisticservice.dtos.PlayerStatistic;
import org.astashonok.statisticservice.services.PlayerStatisticService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController("/statistic/players")
@RequiredArgsConstructor
public class PlayerStatisticController {

    private final PlayerStatisticService playerStatisticService;

    @GetMapping("/{playerId}")
    public PlayerBattlesStatistic getBattlesStatistic(@PathVariable UUID playerId) {
        return playerStatisticService.getBattlesStatistic(playerId);
    }

    @GetMapping
    public List<PlayerStatistic> getPlayersStatistic() {
        return playerStatisticService.getPlayersStatistic();
    }
}
