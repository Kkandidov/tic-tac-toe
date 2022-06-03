package org.astashonok.battleservice.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BattleStatus {

    OPENED,
    IN_PROCESS,
    FINISHED
}
