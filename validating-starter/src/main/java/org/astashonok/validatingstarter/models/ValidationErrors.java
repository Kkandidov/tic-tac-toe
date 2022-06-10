package org.astashonok.validatingstarter.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class ValidationErrors {

    private final String target;
    private final List<String> validationErrors;

    public static ValidationErrors empty() {
        return new ValidationErrors(StringUtils.EMPTY, new ArrayList<>());
    }
}
