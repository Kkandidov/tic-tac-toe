package org.astashonok.battleservice.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.astashonok.battleservice.constants.ErrorMessagePattern;
import org.astashonok.battleservice.constraints.annotations.MoveFormValidated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@MoveFormValidated
public class MoveForm {

    @NotNull(message = ErrorMessagePattern.BATTLE_ID_IS_NULL)
    private final UUID battleId;

    @PositiveOrZero(message = ErrorMessagePattern.X_COORDINATE_NOT_POSITIVE)
    private final int xCoordinate;

    @PositiveOrZero(message = ErrorMessagePattern.Y_COORDINATE_NOT_POSITIVE)
    private final int yCoordinate;
}
