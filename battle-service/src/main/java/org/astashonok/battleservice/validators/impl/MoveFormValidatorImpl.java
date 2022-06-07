package org.astashonok.battleservice.validators.impl;

import lombok.RequiredArgsConstructor;
import org.astashonok.battleservice.constants.BattleConstants;
import org.astashonok.battleservice.constants.ErrorMessage;
import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.models.MoveForm;
import org.astashonok.battleservice.repositories.BattleRepository;
import org.astashonok.battleservice.utils.SecurityUtils;
import org.astashonok.battleservice.utils.ValidationUtils;
import org.astashonok.battleservice.validators.Validator;
import org.astashonok.common.utils.IntegerUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MoveFormValidatorImpl implements Validator<MoveForm> {

    private final BattleRepository battleRepository;

    @Override
    public List<String> validate(@NonNull MoveForm moveForm) {
        List<String> messages = new ArrayList<>();

        Battle battle = battleRepository.findById(moveForm.getBattleId()).orElse(null);
        ValidationUtils.addErrorMessageIfTrue(
                messages,
                String.format(ErrorMessage.BATTLE_NOT_EXISTS, moveForm.getBattleId()),
                () -> battle == null
        );

        if (battle != null) {
            int xCoordinate = moveForm.getXCoordinate();
            int yCoordinate = moveForm.getYCoordinate();

            addMessagesByCoordinates(messages, battle, xCoordinate, yCoordinate);

            ValidationUtils.addErrorMessageIfTrue(
                    messages,
                    ErrorMessage.MOVE_NOT_FOR_CURRENT_USER,
                    () -> !Objects.equals(battle.getNextMoveParticipantId(), SecurityUtils.getCurrentUserId())
            );
        }

        return messages;
    }

    private void addMessagesByCoordinates(List<String> messages, Battle battle, int xCoordinate, int yCoordinate) {
        ValidationUtils.addErrorMessageIfTrue(
                messages,
                String.format(ErrorMessage.COORDINATE_NOT_POSITIVE, BattleConstants.X_NAME, xCoordinate),
                () -> IntegerUtils.isNegative(xCoordinate)
        );

        ValidationUtils.addErrorMessageIfTrue(
                messages,
                String.format(ErrorMessage.COORDINATE_NOT_POSITIVE, BattleConstants.Y_NAME, xCoordinate),
                () -> IntegerUtils.isNegative(yCoordinate)
        );

        ValidationUtils.addErrorMessageIfTrue(
                messages,
                String.format(ErrorMessage.COORDINATES_OF_GAME_BOARD, xCoordinate, yCoordinate),
                () -> IntegerUtils.more(xCoordinate, battle.getBoardHeight())
                        || IntegerUtils.more(yCoordinate, battle.getBoardWidth())
        );
    }
}
