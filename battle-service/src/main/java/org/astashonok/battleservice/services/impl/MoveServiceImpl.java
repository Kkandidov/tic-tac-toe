package org.astashonok.battleservice.services.impl;

import org.astashonok.battleservice.entities.Move;
import org.astashonok.battleservice.repositories.MoveRepository;
import org.astashonok.battleservice.services.MoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoveServiceImpl implements MoveService {

    @Autowired
    private MoveRepository moveRepository;

    @Override
    public List<Move> getMoves(Long battleId) {
        return moveRepository.getAllByBattleId(battleId);
    }
}
