package org.astashonok.battleservice.services;

import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.models.BattleInfo;
import org.astashonok.battleservice.models.BattleCreationForm;
import org.astashonok.battleservice.models.MoveForm;

import java.util.List;

public interface BattleService {

    void makeMove(MoveForm moveForm);

    List<Battle> getOpenedBattles();

    void join(Long battleId);

    Battle openBattle(BattleCreationForm form);

    BattleInfo getBattleInfo(Long battleId);
}
