package org.astashonok.statisticservice.services;

import org.astashonok.statisticservice.dtos.PlayerBattlesStatistic;
import org.astashonok.statisticservice.dtos.PlayerStatistic;

import java.util.List;
import java.util.UUID;

public interface PlayerStatisticService {

    PlayerBattlesStatistic getBattlesStatistic(UUID playerId);

    List<PlayerStatistic> getPlayersStatistic();
}
