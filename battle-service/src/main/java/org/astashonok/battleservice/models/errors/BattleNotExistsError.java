package org.astashonok.battleservice.models.errors;

import org.astashonok.battleservice.constants.ErrorMessagePattern;
import org.astashonok.validatingstarter.models.validationerrors.impl.AbstractErrorMessageBasedOnPatternHolder;

import java.util.UUID;

public class BattleNotExistsError extends AbstractErrorMessageBasedOnPatternHolder {

    public BattleNotExistsError(UUID battleId) {
        super(ErrorMessagePattern.BATTLE_NOT_EXISTS, battleId);
    }
}
