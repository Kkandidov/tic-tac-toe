package org.astashonok.battleservice.constraints.validators;

import lombok.RequiredArgsConstructor;
import org.astashonok.battleservice.constraints.annotations.MoveFormValidated;
import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.models.BattleStatus;
import org.astashonok.battleservice.models.MoveForm;
import org.astashonok.battleservice.models.errors.*;
import org.astashonok.battleservice.repositories.BattleRepository;
import org.astashonok.battleservice.utils.BattleUtils;
import org.astashonok.battleservice.utils.SecurityUtils;
import org.astashonok.common.utils.IntegerUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.UUID;

import static org.astashonok.validatingstarter.utils.ConstraintValidationUtils.addViolationIfNotTrue;

@RequiredArgsConstructor
public class MoveFormValidator implements ConstraintValidator<MoveFormValidated, MoveForm> {

    private final BattleRepository battleRepository;

    @Override
    public boolean isValid(MoveForm form, ConstraintValidatorContext context) {
        UUID battleId = form.getBattleId();
        Battle battle = getBattle(battleId);

        return addViolationIfNotTrue(context, new BattleNotExistsError(battleId),
                () -> battle != null) && isValid(form, context, battle);
    }

    private Battle getBattle(UUID battleId) {
        return battleId == null
                ? null
                : battleRepository.findById(battleId).orElse(null);
    }

    private boolean isValid(MoveForm moveForm, ConstraintValidatorContext context, Battle battle) {
        int xCoordinate = moveForm.getXCoordinate();
        int yCoordinate = moveForm.getYCoordinate();

        return addViolationIfNotTrue(context, new CoordinatesOfGameBoardError(xCoordinate, yCoordinate),
                () -> areCoordinatesInsideBoard(battle, xCoordinate, yCoordinate))
                & addViolationIfNotTrue(context, new MoveNotForCurrentUserError(), () -> isCurrentUserQueue(battle))
                & addViolationIfNotTrue(context, new BattleFinishedError(), () -> !BattleUtils.isFinishedStatus(battle))
                & addViolationIfNotTrue(context, new BattleStatusOpenedError(), () -> !isOpenedStatus(battle));
    }

    private boolean areCoordinatesInsideBoard(Battle battle, int xCoordinate, int yCoordinate) {
        return (IntegerUtils.isNegative(xCoordinate) || IntegerUtils.isNegative(yCoordinate))
                || (IntegerUtils.lessOrEquals(xCoordinate, battle.getBoardHeight())
                && IntegerUtils.lessOrEquals(yCoordinate, battle.getBoardWidth()));
    }

    private boolean isCurrentUserQueue(Battle battle) {
        return Objects.equals(SecurityUtils.getCurrentUserId(), battle.getNextMoveParticipantId());
    }

    private boolean isOpenedStatus(Battle battle) {
        return BattleStatus.OPENED.equals(battle.getStatus());
    }
}
