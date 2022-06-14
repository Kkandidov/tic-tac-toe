package org.astashonok.battleservice.models.errormessages;

import org.astashonok.battleservice.constants.ErrorMessagePattern;
import org.astashonok.validatingstarter.models.errormessages.impl.AbstractErrorMessageBasedOnPatternHolder;

public class BattleFinishedErrorMessageHolder extends AbstractErrorMessageBasedOnPatternHolder {

    public BattleFinishedErrorMessageHolder() {
        super(ErrorMessagePattern.BATTLE_IS_FINISHED);
    }
}
