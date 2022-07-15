package org.astashonok.statisticservice.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder(builderMethodName = "buildPlayerStatistic")
public class PlayerStatistic {

    private UUID playerId;
    private String playerName;
    private String playerLastname;
    private int winNumber;
    private int loseNumber;
    private double winRate;
}
