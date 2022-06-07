package org.astashonok.battleservice.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class MoveForm {

    private final UUID battleId;
    private final int xCoordinate;
    private final int yCoordinate;
}
