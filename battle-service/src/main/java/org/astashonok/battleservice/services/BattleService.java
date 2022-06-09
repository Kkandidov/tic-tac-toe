package org.astashonok.battleservice.services;

import lombok.NonNull;
import org.astashonok.battleservice.dtos.BattleDto;
import org.astashonok.battleservice.models.BattleCreationForm;
import org.astashonok.battleservice.models.BattleState;
import org.astashonok.battleservice.models.MoveForm;

import java.util.List;
import java.util.UUID;

public interface BattleService {

    BattleState makeMove(@NonNull MoveForm moveForm);

    List<BattleDto> getOpenedBattles();

    void join(UUID battleId);

    BattleDto openBattle(@NonNull BattleCreationForm form);
}
