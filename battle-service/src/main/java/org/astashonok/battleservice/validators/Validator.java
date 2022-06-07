package org.astashonok.battleservice.validators;

import lombok.NonNull;

import java.util.List;

public interface Validator<T> {

    List<String> validate(@NonNull T object);
}
