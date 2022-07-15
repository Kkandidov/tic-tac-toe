package org.astashonok.statisticservice.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PlayerBattlesStatistic extends PlayerStatistic {

    private List<PlayerBattleStatistic> battleStatistics;

    @Builder(builderMethodName = "buildPlayerBattlesStatistic")
    public PlayerBattlesStatistic(UUID playerId, String playerName, String playerLastname, int winNumber,
                                  int loseNumber, double winRate, List<PlayerBattleStatistic> battleStatistics) {

        super(playerId, playerName, playerLastname, winNumber, loseNumber, winRate);
        this.battleStatistics = battleStatistics;
    }
}
