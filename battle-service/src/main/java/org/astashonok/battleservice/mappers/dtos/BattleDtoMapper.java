package org.astashonok.battleservice.mappers.dtos;

import org.astashonok.battleservice.dtos.BattleDto;
import org.astashonok.battleservice.entities.Battle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BattleDtoMapper extends ParameterizedDtoMapper<Battle, BattleDto> {
}
