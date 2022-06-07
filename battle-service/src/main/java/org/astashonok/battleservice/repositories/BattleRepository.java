package org.astashonok.battleservice.repositories;

import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.models.BattleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

public interface BattleRepository extends JpaRepository<Battle, UUID> {

    List<Battle> getAllByStatus(BattleStatus battleStatus);
}
