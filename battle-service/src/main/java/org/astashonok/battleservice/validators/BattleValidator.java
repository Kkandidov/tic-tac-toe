package org.astashonok.battleservice.validators;

import lombok.NonNull;
import org.astashonok.battleservice.entities.Battle;

import java.util.List;

public interface BattleValidator {

    List<String> validate(@NonNull Battle battle);
}
