package org.astashonok.battleservice.repositories;

import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.models.BattleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.lang.NonNull;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BattleRepository extends JpaRepository<Battle, UUID> {

    List<Battle> getAllByStatus(BattleStatus battleStatus);

    @NonNull
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    Optional<Battle> findById(@NonNull UUID battleId);
}
