package org.astashonok.battleservice.validators;

import org.astashonok.battleservice.models.MoveForm;
import org.springframework.lang.NonNull;

import java.util.List;

public interface MoveFormValidator {

    List<String> validate(@NonNull MoveForm moveForm);
}
