package org.astashonok.battleservice.models.errors;

import org.astashonok.battleservice.constants.ErrorMessagePattern;
import org.astashonok.validatingstarter.models.validationerrors.impl.AbstractErrorMessageBasedOnPatternHolder;

public class BattleFinishedError extends AbstractErrorMessageBasedOnPatternHolder {

    public BattleFinishedError() {
        super(ErrorMessagePattern.BATTLE_IS_FINISHED);
    }
}
