package org.astashonok.validatingstarter.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ValidationErrors {

    private final String target;

    @NonNull
    private final List<String> validationErrors;
}
