package org.astashonok.battleservice.repositories;

import org.astashonok.battleservice.entities.Move;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoveRepository extends JpaRepository<Move, Long> {

    List<Move> getAllByBattleId(Long battleId);
}
