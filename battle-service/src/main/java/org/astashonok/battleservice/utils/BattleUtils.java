package org.astashonok.battleservice.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.models.BattleStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BattleUtils {

    public static boolean isFinishedStatus(@NonNull Battle battle) {
        return BattleStatus.FINISHED.equals(battle.getStatus()) || battle.getRemainingFreeMoveCount() == 0;
    }
}
