package org.astashonok.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtils {

    public static <T, R> List<R> mapList(@NonNull List<T> mappingObjects, @NonNull Function<T, R> objectMapper) {
        return mappingObjects.stream()
                .filter(Objects::nonNull)
                .map(objectMapper)
                .collect(Collectors.toList());
    }

    public static <T, R> Set<R> mapSetOrEmpty(Set<T> mappingSet, @NonNull Function<T, R> objectMapper) {
        return CollectionUtils.isEmpty(mappingSet)
                ? Collections.emptySet()
                : mappingSet.stream()
                .filter(Objects::nonNull)
                .map(objectMapper)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
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

    public static <T, R> R getOrSupplyDefault(T object, @NonNull Function<T, R> objectGetter,
                                              Supplier<R> defaultValueSupplier) {

        return getOrDefault(object, objectGetter, defaultValueSupplier.get());
    }

    public static <T, R> R getOrDefault(T object, @NonNull Function<T, R> objectGetter, R defaultValue) {
        return Optional.ofNullable(object)
                .map(objectGetter)
                .orElse(defaultValue);
    }

    public static <T, R> R applyOrNull(T object, @NonNull Function<T, R> function) {
        return object == null
                ? null
                : function.apply(object);
    }
}
