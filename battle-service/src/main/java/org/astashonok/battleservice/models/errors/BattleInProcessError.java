package org.astashonok.battleservice.models.errors;

import org.astashonok.battleservice.constants.ErrorMessagePattern;
import org.astashonok.validatingstarter.models.validationerrors.impl.AbstractErrorMessageBasedOnPatternHolder;

public class BattleInProcessError extends AbstractErrorMessageBasedOnPatternHolder {

    public BattleInProcessError() {
        super(ErrorMessagePattern.BATTLE_IN_PROCESS);
    }
}
