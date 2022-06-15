package org.astashonok.validatingstarter.models.errormessages.impl;

import lombok.Getter;
import lombok.NonNull;
import org.astashonok.validatingstarter.models.errormessages.ValidationError;

@Getter
public abstract class AbstractErrorMessageBasedOnPatternHolder implements ValidationError {

    private final String errorMessage;

    public AbstractErrorMessageBasedOnPatternHolder(@NonNull String errorPattern, Object... args) {
        this.errorMessage = String.format(errorPattern, args);
    }
}
