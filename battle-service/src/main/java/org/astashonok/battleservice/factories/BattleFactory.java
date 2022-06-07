package org.astashonok.battleservice.factories;

import lombok.NonNull;
import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.models.BattleCreationForm;

public interface BattleFactory {

    Battle create(@NonNull BattleCreationForm form);

    Battle crateDefault();
}
