package org.astashonok.battleservice.models;

import lombok.Data;

import java.util.UUID;

@Data
public class BattleState {

    private BattleStatus status;
    private UUID winnerId;
}
