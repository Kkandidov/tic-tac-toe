package org.astashonok.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtils {

    public static <T, R> List<R> mapList(@NonNull List<T> mappingObjects, @NonNull Function<T, R> objectMapper) {
        return CollectionUtils.isEmpty(mappingObjects)
                ? Collections.emptyList()
                : mappingObjects.stream()
                .filter(Objects::nonNull)
                .map(objectMapper)
                .collect(Collectors.toList());
    }
}
