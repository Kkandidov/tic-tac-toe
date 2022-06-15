package org.astashonok.battleservice.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BattleState {

    private BattleStatus status;
    private UUID winnerId;
}
