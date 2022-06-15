package org.astashonok.battleservice.components.calculators;

import lombok.NonNull;
import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.models.BattleState;

public interface BattleStateCalculator {

    BattleState calculateAndGet(@NonNull Battle battle);
}
