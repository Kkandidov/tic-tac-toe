package org.astashonok.battleservice.constraints.validators;

import lombok.RequiredArgsConstructor;
import org.astashonok.battleservice.constants.ErrorMessage;
import org.astashonok.battleservice.constraints.annotations.MoveFormValidated;
import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.models.BattleStatus;
import org.astashonok.battleservice.models.MoveForm;
import org.astashonok.battleservice.repositories.BattleRepository;
import org.astashonok.battleservice.utils.SecurityUtils;
import org.astashonok.common.utils.IntegerUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

import static org.astashonok.validatingstarter.utils.ConstraintValidationUtils.addViolationIfNotTrue;

@RequiredArgsConstructor
public class MoveFormValidator implements ConstraintValidator<MoveFormValidated, MoveForm> {

    private final BattleRepository battleRepository;

    @Override
    public boolean isValid(MoveForm form, ConstraintValidatorContext context) {
        Battle battle = battleRepository.findById(form.getBattleId()).orElse(null);

        return addViolationIfNotTrue(context, String.format(ErrorMessage.BATTLE_NOT_EXISTS, form.getBattleId()),
                () -> battle != null) && isValid(form, context, battle);
    }

    private boolean isValid(MoveForm moveForm, ConstraintValidatorContext context, Battle battle) {
        int xCoordinate = moveForm.getXCoordinate();
        int yCoordinate = moveForm.getYCoordinate();

        return addViolationIfNotTrue(context, String.format(ErrorMessage.COORDINATES_OF_GAME_BOARD, xCoordinate,
                yCoordinate), () -> areCoordinatesInsideBoard(battle, xCoordinate, yCoordinate))
                & addViolationIfNotTrue(context, ErrorMessage.MOVE_NOT_FOR_CURRENT_USER, () -> isCurrentUserQueue(battle))
                & addViolationIfNotTrue(context, ErrorMessage.BATTLE_IS_FINISHED, () -> !isFinishedStatus(battle))
                & addViolationIfNotTrue(context, ErrorMessage.BATTLE_STATUS_IS_OPENED, () -> !isOpenedStatus(battle));
    }

    private boolean areCoordinatesInsideBoard(Battle battle, int xCoordinate, int yCoordinate) {
        return (IntegerUtils.isNegative(xCoordinate) || IntegerUtils.isNegative(yCoordinate))
                || (IntegerUtils.lessOrEquals(xCoordinate, battle.getBoardHeight())
                && IntegerUtils.lessOrEquals(yCoordinate, battle.getBoardWidth()));
    }

    private boolean isCurrentUserQueue(Battle battle) {
        return Objects.equals(SecurityUtils.getCurrentUserId(), battle.getNextMoveParticipantId());
    }

    private boolean isFinishedStatus(Battle battle) {
        return BattleStatus.FINISHED.equals(battle.getStatus()) || battle.getRemainingFreeMoveCount() == 0;
    }

    private boolean isOpenedStatus(Battle battle) {
        return BattleStatus.OPENED.equals(battle.getStatus());
    }
}
