package org.astashonok.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtils {

    public static <T, R> List<R> mapList(@NonNull List<T> mappingObjects, @NonNull Function<T, R> objectMapper) {
        return mappingObjects.stream()
                .filter(Objects::nonNull)
                .map(objectMapper)
                .collect(Collectors.toList());
    }

    public static <T> List<T> joinNotEmpty(@NonNull List<List<T>> lists) {
        return lists.stream()
                .filter(CollectionUtils::isNotEmpty)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public static <T> void addAllIfNotEmpty(@NonNull Collection<T> collection, Collection<T> addedCollection) {
        if (CollectionUtils.isNotEmpty(addedCollection)) {
            collection.addAll(addedCollection);
        }
    }

    public static <T, R> R getOrDefault(T object, @NonNull Function<T, R> objectGetter, R defaultValue) {
        return Optional.ofNullable(object)
                .map(objectGetter)
                .orElse(defaultValue);
    }
}
