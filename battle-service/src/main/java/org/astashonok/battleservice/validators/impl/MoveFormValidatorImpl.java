package org.astashonok.battleservice.validators.impl;

import org.astashonok.battleservice.constants.ErrorMessage;
import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.models.MoveForm;
import org.astashonok.battleservice.repositories.BattleRepository;
import org.astashonok.battleservice.services.AuthUserService;
import org.astashonok.battleservice.validators.MoveFormValidator;
import org.astashonok.common.utils.IntegerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@Component
public class MoveFormValidatorImpl implements MoveFormValidator {

    private static final String X_NAME = "x";
    private static final String Y_NAME = "y";

    @Autowired
    private BattleRepository battleRepository;

    @Autowired
    private AuthUserService authUserService;

    @Override
    public List<String> validate(@NonNull MoveForm moveForm) {
        List<String> messages = new ArrayList<>();

        Battle battle = battleRepository.findById(moveForm.getBattleId()).orElse(null);
        if (battle == null) {
            messages.add(String.format(ErrorMessage.BATTLE_NOT_EXISTS, moveForm.getBattleId()));
        } else {
            int xCoordinate = moveForm.getXCoordinate();
            int yCoordinate = moveForm.getYCoordinate();

            addMessagesByCoordinates(messages, battle, xCoordinate, yCoordinate);

            addMessageIfTrue(
                    messages,
                    ErrorMessage.MOVE_NOT_FOR_CURRENT_USER,
                    () -> !Objects.equals(battle.getNextMoveParticipantId(), authUserService.getCurrentUserId())
            );

        }

        return messages;
    }

    private void addMessagesByCoordinates(List<String> messages, Battle battle, int xCoordinate, int yCoordinate) {
        addMessageIfTrue(
                messages,
                String.format(ErrorMessage.COORDINATE_NOT_POSITIVE, X_NAME, xCoordinate),
                () -> IntegerUtils.isNegative(xCoordinate)
        );

        addMessageIfTrue(
                messages,
                String.format(ErrorMessage.COORDINATE_NOT_POSITIVE, Y_NAME, xCoordinate),
                () -> IntegerUtils.isNegative(yCoordinate)
        );

        addMessageIfTrue(
                messages,
                String.format(ErrorMessage.COORDINATES_OF_GAME_BOARD, xCoordinate, yCoordinate),
                () -> IntegerUtils.more(xCoordinate, battle.getBoardHeight())
                        || IntegerUtils.more(yCoordinate, battle.getBoardWidth())
        );
    }

    void addMessageIfTrue(List<String> exceptionMessages, String message, Supplier<Boolean> isTrue) {
        if (Boolean.TRUE.equals(isTrue.get())) {
            exceptionMessages.add(message);
        }
    }
}
