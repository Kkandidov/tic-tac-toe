package org.astashonok.battleservice.models;

import lombok.Data;

@Data
public class MoveForm {

    private final Long battleId;
    private final int xCoordinate;
    private final int yCoordinate;
}
