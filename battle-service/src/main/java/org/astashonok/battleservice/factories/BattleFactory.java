package org.astashonok.battleservice.factories;

import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.models.BattleCreationForm;

public interface BattleFactory {

    Battle create(BattleCreationForm form);

    Battle crateDefault();
}
