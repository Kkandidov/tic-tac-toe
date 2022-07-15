package org.astashonok.battleservice.models.errors;

import org.astashonok.battleservice.constants.ErrorMessagePattern;
import org.astashonok.validatingstarter.models.validationerrors.impl.AbstractErrorMessageBasedOnPatternHolder;

public class BattleStatusOpenedError extends AbstractErrorMessageBasedOnPatternHolder {

    public BattleStatusOpenedError() {
        super(ErrorMessagePattern.BATTLE_STATUS_IS_OPENED);
    }
}
