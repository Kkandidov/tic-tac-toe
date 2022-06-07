package org.astashonok.battleservice.services;

import lombok.NonNull;
import org.astashonok.battleservice.dtos.BattleDto;
import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.models.BattleCreationForm;
import org.astashonok.battleservice.models.BattleInfo;
import org.astashonok.battleservice.models.MoveForm;

import java.util.List;
import java.util.UUID;

public interface BattleService {

    void makeMove(@NonNull MoveForm moveForm);

    List<BattleDto> getOpenedBattles();

    void join(UUID battleId);

    BattleDto openBattle(@NonNull BattleCreationForm form);

    BattleInfo getBattleInfo(UUID battleId);
}
