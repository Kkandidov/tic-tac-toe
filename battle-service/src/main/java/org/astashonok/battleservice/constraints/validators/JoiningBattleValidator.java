package org.astashonok.battleservice.constraints.validators;

import lombok.RequiredArgsConstructor;
import org.astashonok.battleservice.constraints.annotations.JoiningBattleValidated;
import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.models.BattleStatus;
import org.astashonok.battleservice.models.errormessages.BattleFinishedErrorMessageHolder;
import org.astashonok.battleservice.models.errormessages.BattleInProcessErrorMessageHolder;
import org.astashonok.battleservice.models.errormessages.BattleNotExistsErrorMessageHolder;
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

        return addViolationIfNotTrue(context, new BattleNotExistsErrorMessageHolder(battleId),
                () -> battle != null) && isValid(context, battle);
    }

    private boolean isValid(ConstraintValidatorContext context, Battle battle) {
        return addViolationIfNotTrue(context, new BattleInProcessErrorMessageHolder(), () -> !isBattleInProcess(battle))
                & addViolationIfNotTrue(context, new BattleFinishedErrorMessageHolder(),
                () -> !BattleUtils.isFinishedStatus(battle));
    }

    private boolean isBattleInProcess(Battle battle) {
        return BattleStatus.IN_PROCESS.equals(battle.getStatus())
                || battle.getOParticipantId() != null;
    }
}
