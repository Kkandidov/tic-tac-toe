package org.astashonok.battleservice.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationUtils {

    public static <T> void asserts(@NonNull T validationObject, @NonNull Function<T, List<String>> validateMethod,
                                   @NonNull Function<String, RuntimeException> exceptionConstructor) {

        List<String> errorMessages = validateMethod.apply(validationObject);
        if (!CollectionUtils.isEmpty(errorMessages)) {
            throw exceptionConstructor.apply(errorMessages.toString());
        }
    }

    public static <T> T assertAndGet(@NonNull T validationObject, Function<T, @NonNull List<String>> validateMethod,
                                     @NonNull Function<String, RuntimeException> exceptionConstructor) {

        asserts(validationObject, validateMethod, exceptionConstructor);
        return validationObject;
    }

    public static void addErrorMessageIfTrue(@NonNull List<String> errorMessages, @NonNull String errorMessage,
                                             @NonNull Supplier<Boolean> isTrue) {

        if (Boolean.TRUE.equals(isTrue.get())) {
            errorMessages.add(errorMessage);
        }
    }
}
