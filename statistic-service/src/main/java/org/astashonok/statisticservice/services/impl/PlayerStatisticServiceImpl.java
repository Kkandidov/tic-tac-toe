package org.astashonok.statisticservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.astashonok.statisticservice.dtos.PlayerBattlesStatistic;
import org.astashonok.statisticservice.dtos.PlayerStatistic;
import org.astashonok.statisticservice.services.PlayerStatisticService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PlayerStatisticServiceImpl implements PlayerStatisticService {

    @Override
    public PlayerBattlesStatistic getBattlesStatistic(UUID playerId) {
        return null;
    }

    @Override
    public List<PlayerStatistic> getPlayersStatistic() {
        return null;
    }

    @Override
    public List<PlayerStatistic> getTopPlayersStatistic(int playerNumber) {
        return null;
    }
}
