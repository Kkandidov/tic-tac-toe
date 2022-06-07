package org.astashonok.battleservice.validators.impl;

import lombok.NonNull;
import org.astashonok.battleservice.constants.ErrorMessage;
import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.models.BattleStatus;
import org.astashonok.battleservice.utils.ValidationUtils;
import org.astashonok.battleservice.validators.Validator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BattleValidatorImpl implements Validator<Battle> {

    @Override
    public List<String> validate(@NonNull Battle battle) {
        List<String> messages = new ArrayList<>();

        ValidationUtils.addErrorMessageIfTrue(
                messages,
                ErrorMessage.BATTLE_IS_FINISHED,
                () -> BattleStatus.FINISHED.equals(battle.getStatus()) || battle.getRemainingFreeMoveCount() == 0
        );

        ValidationUtils.addErrorMessageIfTrue(
                messages,
                ErrorMessage.BATTLE_STATUS_IS_OPENED,
                () -> BattleStatus.OPENED.equals(battle.getStatus())
        );

        return messages;
    }
}
