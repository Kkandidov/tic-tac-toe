package org.astashonok.battleservice.constraints.validators;

import lombok.RequiredArgsConstructor;
import org.astashonok.battleservice.constraints.annotations.JoiningBattleValidated;
import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.models.BattleStatus;
import org.astashonok.battleservice.models.errormessages.BattleFinishedError;
import org.astashonok.battleservice.models.errormessages.BattleInProcessError;
import org.astashonok.battleservice.models.errormessages.BattleNotExistsError;
import org.astashonok.battleservice.repositories.BattleRepository;
import org.astashonok.battleservice.utils.BattleUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.UUID;

import static org.astashonok.validatingstarter.utils.ConstraintValidationUtils.addViolationIfNotTrue;

@RequiredArgsConstructor
public class JoiningBattleValidator implements ConstraintValidator<JoiningBattleValidated, UUID> {

    private final BattleRepository battleRepository;

    @Override
    public boolean isValid(UUID battleId, ConstraintValidatorContext context) {
        Battle battle = battleRepository.findById(battleId).orElse(null);

        return addViolationIfNotTrue(context, new BattleNotExistsError(battleId),
                () -> battle != null) && isValid(context, battle);
    }

    private boolean isValid(ConstraintValidatorContext context, Battle battle) {
        return addViolationIfNotTrue(context, new BattleInProcessError(), () -> !isBattleInProcess(battle))
                & addViolationIfNotTrue(context, new BattleFinishedError(),
                () -> !BattleUtils.isFinishedStatus(battle));
    }

    private boolean isBattleInProcess(Battle battle) {
        return BattleStatus.IN_PROCESS.equals(battle.getStatus())
                || battle.getOParticipantId() != null;
    }
}
