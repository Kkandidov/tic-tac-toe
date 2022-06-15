package org.astashonok.validatingstarter.utils;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.astashonok.validatingstarter.models.errormessages.ValidationError;

import javax.validation.ConstraintValidatorContext;
import java.util.function.Supplier;

@NoArgsConstructor
public class ConstraintValidationUtils {

    public static void addViolation(@NonNull ConstraintValidatorContext context,
                                    @NonNull ValidationError errorMessageHolder) {

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorMessageHolder.getErrorMessage())
                .addConstraintViolation();
    }

    public static boolean addViolationIfNotTrue(@NonNull ConstraintValidatorContext context,
                                                @NonNull ValidationError errorMessageHolder,
                                                @NonNull Supplier<Boolean> validateAction) {

        boolean isValid = validateAction.get();
        if (!isValid) {
            ConstraintValidationUtils.addViolation(context, errorMessageHolder);
        }

        return isValid;
    }
}
