package org.astashonok.validatingstarter.utils;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.astashonok.validatingstarter.models.validationerrors.ValidationError;

import javax.validation.ConstraintValidatorContext;
import java.util.function.Supplier;

@NoArgsConstructor
public class ConstraintValidationUtils {

    public static void addViolation(@NonNull ConstraintValidatorContext context,
                                    @NonNull ValidationError validationError) {

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(validationError.getErrorMessage())
                .addConstraintViolation();
    }

    public static boolean addViolationIfNotTrue(@NonNull ConstraintValidatorContext context,
                                                @NonNull ValidationError validationError,
                                                @NonNull Supplier<Boolean> validateAction) {

        boolean isValid = validateAction.get();
        if (!isValid) {
            ConstraintValidationUtils.addViolation(context, validationError);
        }

        return isValid;
    }
}
