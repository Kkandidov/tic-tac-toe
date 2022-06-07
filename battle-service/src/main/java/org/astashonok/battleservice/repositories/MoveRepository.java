package org.astashonok.battleservice.repositories;

import org.astashonok.battleservice.entities.Move;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MoveRepository extends JpaRepository<Move, UUID> {

    List<Move> getAllByBattleId(UUID battleId);
}
