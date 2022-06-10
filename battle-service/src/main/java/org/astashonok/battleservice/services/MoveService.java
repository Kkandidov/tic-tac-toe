package org.astashonok.battleservice.services;

import lombok.NonNull;
import org.astashonok.battleservice.dtos.MoveDto;

import java.util.List;
import java.util.UUID;

public interface MoveService {

    List<MoveDto> getMoves(@NonNull UUID battleId);
}
