package org.astashonok.battleservice.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.astashonok.battleservice.dtos.MoveDto;
import org.astashonok.battleservice.mappers.dtos.MoveDtoMapper;
import org.astashonok.battleservice.repositories.MoveRepository;
import org.astashonok.battleservice.services.MoveService;
import org.astashonok.common.utils.CommonUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MoveServiceImpl implements MoveService {

    private final MoveRepository moveRepository;

    private final MoveDtoMapper moveDtoMapper;

    @Override
    public List<MoveDto> getMoves(@NonNull UUID battleId) {
        return CommonUtils.mapList(moveRepository.getAllByBattleId(battleId), moveDtoMapper::toDto);
    }
}
