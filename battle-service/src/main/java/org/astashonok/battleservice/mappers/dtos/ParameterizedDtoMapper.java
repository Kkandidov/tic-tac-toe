package org.astashonok.battleservice.mappers.dtos;

public interface ParameterizedDtoMapper<E, D> {

    D toDto(E entity);

    E toEntity(D dto);
}
