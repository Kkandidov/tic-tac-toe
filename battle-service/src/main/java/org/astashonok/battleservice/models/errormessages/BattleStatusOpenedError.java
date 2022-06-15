package org.astashonok.battleservice.models.errormessages;

import org.astashonok.battleservice.constants.ErrorMessagePattern;
import org.astashonok.validatingstarter.models.errormessages.impl.AbstractErrorMessageBasedOnPatternHolder;

public class BattleStatusOpenedError extends AbstractErrorMessageBasedOnPatternHolder {

    public BattleStatusOpenedError() {
        super(ErrorMessagePattern.BATTLE_STATUS_IS_OPENED);
    }
}
