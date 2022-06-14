package org.astashonok.battleservice.models.errormessages;

import org.astashonok.battleservice.constants.ErrorMessagePattern;
import org.astashonok.validatingstarter.models.errormessages.impl.AbstractErrorMessageBasedOnPatternHolder;

public class BattleInProcessErrorMessageHolder extends AbstractErrorMessageBasedOnPatternHolder {

    public BattleInProcessErrorMessageHolder() {
        super(ErrorMessagePattern.BATTLE_IN_PROCESS);
    }
}
