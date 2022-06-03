package org.astashonok.battleservice.factories.impl;

import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.exceptions.BattleCreationFormException;
import org.astashonok.battleservice.factories.BattleFactory;
import org.astashonok.battleservice.models.BattleCreationForm;
import org.astashonok.battleservice.services.AuthUserService;
import org.astashonok.battleservice.validators.BattleCreationFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Component
public class BattleFactoryImpl implements BattleFactory {

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private BattleCreationFormValidator battleCreationFormValidator;

    @Override
    public Battle create(BattleCreationForm form) {
        List<String> errorMessages = battleCreationFormValidator.validate(form);

        if (!CollectionUtils.isEmpty(errorMessages)) {
            throw new BattleCreationFormException(errorMessages.toString());
        }

        return Optional.of(form)
                .map(f -> new Battle(
                                authUserService.getCurrentUserId(),
                                f.getBoardHeight(),
                                f.getBoardWidth(),
                                f.getWinningNumberInRow()
                        )
                )
                .orElseGet(this::crateDefault);
    }

    @Override
    public Battle crateDefault() {
        return new Battle(authUserService.getCurrentUserId());
    }
}
