package org.astashonok.battleservice.constraints.validators;

import org.astashonok.battleservice.constraints.annotations.BattleCreationFormValidated;
import org.astashonok.battleservice.models.BattleCreationForm;
import org.astashonok.battleservice.models.errormessages.IncorrectWinningNumberRowErrorMessageHolder;
import org.astashonok.validatingstarter.utils.ConstraintValidationUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BattleCreationFormValidator implements ConstraintValidator<BattleCreationFormValidated, BattleCreationForm> {

    @Override
    public boolean isValid(BattleCreationForm form, ConstraintValidatorContext context) {
        int winningNumberInRow = form.getWinningNumberInRow();
        int boardHeight = form.getBoardHeight();
        int boardWidth = form.getBoardWidth();

        return ConstraintValidationUtils.addViolationIfNotTrue(
                context,
                new IncorrectWinningNumberRowErrorMessageHolder(winningNumberInRow, boardWidth, boardHeight),
                () -> winningNumberInRow <= boardHeight && winningNumberInRow <= boardWidth
        );
    }
}
