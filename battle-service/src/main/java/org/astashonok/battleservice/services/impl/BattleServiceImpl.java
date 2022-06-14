package org.astashonok.battleservice.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.astashonok.battleservice.components.calculators.BattleStateCalculator;
import org.astashonok.battleservice.dtos.BattleDto;
import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.entities.Move;
import org.astashonok.battleservice.factories.BattleFactory;
import org.astashonok.battleservice.mappers.dtos.BattleDtoMapper;
import org.astashonok.battleservice.models.BattleCreationForm;
import org.astashonok.battleservice.models.BattleState;
import org.astashonok.battleservice.models.BattleStatus;
import org.astashonok.battleservice.models.MoveForm;
import org.astashonok.battleservice.repositories.BattleRepository;
import org.astashonok.battleservice.repositories.MoveRepository;
import org.astashonok.battleservice.services.BattleService;
import org.astashonok.battleservice.utils.SecurityUtils;
import org.astashonok.common.utils.CommonUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {

    private final BattleRepository battleRepository;
    private final MoveRepository moveRepository;
    private final BattleFactory battleFactory;
    private final BattleStateCalculator battleStateCalculator;
    private final BattleDtoMapper battleDtoMapper;

    @Override
    public BattleState makeMove(@NonNull MoveForm moveForm) {
        Battle battle = Objects.requireNonNull(battleRepository.findById(moveForm.getBattleId()).orElse(null));
        return makeMove(battle, moveForm);
    }

    @Override
    public List<BattleDto> getOpenedBattles() {
        return CommonUtils.mapList(battleRepository.getAllByStatus(BattleStatus.OPENED), battleDtoMapper::toDto);
    }

    @Override
    public BattleDto join(@NonNull UUID battleId) {
        Battle battle = Objects.requireNonNull(battleRepository.findById(battleId).orElse(null));
        joinBattle(battle);
        battleRepository.save(battle);
        return battleDtoMapper.toDto(battle);
    }

    @Override
    public BattleDto openBattle(@NonNull BattleCreationForm form) {
        return battleDtoMapper.toDto(battleRepository.save(battleFactory.create(form)));
    }

    private BattleState makeMove(Battle battle, MoveForm moveForm) {
        battle.setFieldNumberValue(SecurityUtils.getCurrentUserId(), moveForm.getXCoordinate(), moveForm.getYCoordinate());
        battle.decrementRemainingFreeMoveCount();
        battle.swapNextMoveParticipantId();

        BattleState battleState = battleStateCalculator.calculateAndGet(battle);
        battle.setWinnerId(battleState.getWinnerId());
        battle.setStatus(battleState.getStatus());

        battleRepository.save(battle);
        saveUserMove(moveForm);

        return battleState;
    }

    private void saveUserMove(MoveForm moveForm) {
        Move move = new Move();
        move.setBattleId(moveForm.getBattleId());
        move.setUserId(SecurityUtils.getCurrentUserId());
        move.setXCoordinate(moveForm.getXCoordinate());
        move.setYCoordinate(moveForm.getYCoordinate());
        move.setTime(LocalDateTime.now());

        moveRepository.save(move);
    }

    private void joinBattle(Battle battle) {
        battle.setOParticipantId(SecurityUtils.getCurrentUserId());
        battle.setStatus(BattleStatus.IN_PROCESS);
    }
}