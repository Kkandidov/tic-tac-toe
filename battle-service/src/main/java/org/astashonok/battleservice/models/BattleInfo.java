package org.astashonok.battleservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.astashonok.battleservice.models.BattleStatus;

@Data
public class BattleInfo {

    private final BattleStatus status;
    private final Long winnerId;
}
