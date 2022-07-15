package org.astashonok.statisticservice.repositories;

import org.astashonok.statisticservice.entities.BattleStatistic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BattleStatisticRepository extends JpaRepository<BattleStatistic, UUID> {
}
