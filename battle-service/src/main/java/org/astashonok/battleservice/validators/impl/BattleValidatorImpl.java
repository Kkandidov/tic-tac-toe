package org.astashonok.battleservice.validators.impl;

import lombok.NonNull;
import org.astashonok.battleservice.constants.ErrorMessage;
import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.models.BattleStatus;
import org.astashonok.battleservice.validators.BattleValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Component
public class BattleValidatorImpl implements BattleValidator {

    @Override
    public List<String> validate(@NonNull Battle battle) {
        List<String> messages = new ArrayList<>();

        addMessageIfTrue(
                messages,
                ErrorMessage.BATTLE_IS_FINISHED,
                () -> BattleStatus.FINISHED.equals(battle.getStatus()) || battle.getRemainingFreeMoveCount() == 0
        );

        addMessageIfTrue(
                messages,
                ErrorMessage.BATTLE_STATUS_IS_OPENED,
                () -> BattleStatus.OPENED.equals(battle.getStatus())
        );

        return messages;
    }

    void addMessageIfTrue(List<String> exceptionMessages, String message, Supplier<Boolean> isTrue) {
        if (Boolean.TRUE.equals(isTrue.get())) {
            exceptionMessages.add(message);
        }
    }
}
