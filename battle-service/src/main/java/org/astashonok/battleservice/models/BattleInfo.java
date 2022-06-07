package org.astashonok.battleservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.astashonok.battleservice.models.BattleStatus;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class BattleInfo {

    private final BattleStatus status;
    private final UUID winnerId;
}
