package org.astashonok.validatingstarter.utils;

import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.ConstraintValidatorContext;
import java.util.function.Supplier;

@NoArgsConstructor
public class ConstraintValidationUtils {

    public static void addViolation(@NonNull ConstraintValidatorContext context, @NonNull String violationMessageTemplate) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(violationMessageTemplate)
                .addConstraintViolation();
    }

    public static boolean addViolationIfNotTrue(@NonNull ConstraintValidatorContext context, @NonNull String errorMessage,
                                                @NonNull Supplier<Boolean> validateAction) {

        boolean isValid = validateAction.get();
        if (!isValid) {
            ConstraintValidationUtils.addViolation(context, errorMessage);
        }

        return isValid;
    }
}
