package org.astashonok.battleservice.models.errormessages;

import org.astashonok.battleservice.constants.ErrorMessagePattern;
import org.astashonok.validatingstarter.models.errormessages.impl.AbstractErrorMessageBasedOnPatternHolder;

import java.util.UUID;

public class BattleNotExistsError extends AbstractErrorMessageBasedOnPatternHolder {

    public BattleNotExistsError(UUID battleId) {
        super(ErrorMessagePattern.BATTLE_NOT_EXISTS, battleId);
    }
}
