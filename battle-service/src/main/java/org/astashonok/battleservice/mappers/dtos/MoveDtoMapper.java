package org.astashonok.battleservice.mappers.dtos;

import org.astashonok.battleservice.dtos.MoveDto;
import org.astashonok.battleservice.entities.Move;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MoveDtoMapper extends ParameterizedDtoMapper<Move, MoveDto> {
}
