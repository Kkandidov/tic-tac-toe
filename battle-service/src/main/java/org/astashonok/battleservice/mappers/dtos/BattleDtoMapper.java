package org.astashonok.battleservice.mappers.dtos;

import org.astashonok.battleservice.dtos.BattleDto;
import org.astashonok.battleservice.entities.Battle;
import org.astashonok.battleservice.mappers.dtos.ParameterizedMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BattleDtoMapper extends ParameterizedMapper<Battle, BattleDto> {
}
