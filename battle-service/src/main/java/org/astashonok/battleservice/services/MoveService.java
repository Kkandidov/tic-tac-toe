package org.astashonok.battleservice.services;

import org.astashonok.battleservice.entities.Move;

import java.util.List;

public interface MoveService {

    List<Move> getMoves(Long battleId);
}
