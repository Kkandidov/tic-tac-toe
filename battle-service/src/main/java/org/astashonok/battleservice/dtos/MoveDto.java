package org.astashonok.battleservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class MoveDto {

    private UUID id;
    private UUID battleId;
    private UUID userId;
    private int xCoordinate;
    private int yCoordinate;
    private LocalDateTime time;
}
