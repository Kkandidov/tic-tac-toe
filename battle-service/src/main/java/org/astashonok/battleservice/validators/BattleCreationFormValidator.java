package org.astashonok.battleservice.validators;

import lombok.NonNull;
import org.astashonok.battleservice.models.BattleCreationForm;

import java.util.List;

public interface BattleCreationFormValidator {

    List<String> validate(@NonNull BattleCreationForm form);
}
