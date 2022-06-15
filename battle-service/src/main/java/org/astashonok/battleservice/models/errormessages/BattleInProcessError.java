package org.astashonok.battleservice.models.errormessages;

import org.astashonok.battleservice.constants.ErrorMessagePattern;
import org.astashonok.validatingstarter.models.errormessages.impl.AbstractErrorMessageBasedOnPatternHolder;

public class BattleInProcessError extends AbstractErrorMessageBasedOnPatternHolder {

    public BattleInProcessError() {
        super(ErrorMessagePattern.BATTLE_IN_PROCESS);
    }
}
