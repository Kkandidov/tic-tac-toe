package org.astashonok.validatingstarter.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ValidationErrorsHolder {

    @NonNull
    private final List<ValidationErrors> errors = new ArrayList<>();

    public ValidationErrorsHolder(@NonNull List<ValidationErrors> errors) {
        this.errors.addAll(errors);
    }

    public ValidationErrorsHolder(@NonNull ValidationErrors errors) {
        this.errors.add(errors);
    }

    public static ValidationErrorsHolder empty() {
        return new ValidationErrorsHolder();
    }
}
