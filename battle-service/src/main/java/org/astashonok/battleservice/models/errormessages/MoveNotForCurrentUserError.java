package org.astashonok.battleservice.models.errormessages;

import org.astashonok.battleservice.constants.ErrorMessagePattern;
import org.astashonok.validatingstarter.models.errormessages.impl.AbstractErrorMessageBasedOnPatternHolder;

public class MoveNotForCurrentUserError extends AbstractErrorMessageBasedOnPatternHolder {

    public MoveNotForCurrentUserError() {
        super(ErrorMessagePattern.MOVE_NOT_FOR_CURRENT_USER);
    }
}
