package org.astashonok.battleservice.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.astashonok.battleservice.components.calculators.BattleStateCalculator;
import org.astashonok.battleservice.dtos.BattleDto;
import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.entities.Move;
import org.astashonok.battleservice.exceptions.BattleException;
import org.astashonok.battleservice.exceptions.MoveException;
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
import org.astashonok.battleservice.utils.ValidationUtils;
import org.astashonok.battleservice.validators.Validator;
import org.astashonok.common.utils.CommonUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.astashonok.battleservice.constants.ErrorMessage.BATTLE_NOT_EXISTS;

@Service
@Transactional
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {

    private final BattleRepository battleRepository;

    private final MoveRepository moveRepository;

    private final BattleFactory battleFactory;

    private final Validator<MoveForm> moveFormValidator;

    private final Validator<Battle> battleValidator;

    private final BattleStateCalculator battleStateCalculator;

    private final BattleDtoMapper battleDtoMapper;

    @Override
    public BattleState makeMove(@NonNull MoveForm moveForm) {
        ValidationUtils.asserts(moveForm, moveFormValidator::validate, MoveException::new);

        return Optional.of(moveForm.getBattleId())
                .flatMap(battleRepository::findById)
                .map(battle -> ValidationUtils.assertAndGet(battle, battleValidator::validate, BattleException::new))
                .map(battle -> makeMove(battle, moveForm))
                .orElse(null);
    }

    @Override
    public List<BattleDto> getOpenedBattles() {
        return CommonUtils.mapList(battleRepository.getAllByStatus(BattleStatus.OPENED), battleDtoMapper::toDto);
    }

    @Override
    public void join(UUID battleId) {
        Optional.ofNullable(battleId)
                .flatMap(battleRepository::findById)
                .map(this::joinBattleAndGet)
                .map(battleRepository::save)
                .orElseThrow(() -> new BattleException(String.format(BATTLE_NOT_EXISTS, battleId)));
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

    private Battle joinBattleAndGet(Battle battle) {
        battle.setOParticipantId(SecurityUtils.getCurrentUserId());
        battle.setStatus(BattleStatus.IN_PROCESS);
        return battle;
    }
}