package org.astashonok.battleservice.mappers.dtos;

public interface ParameterizedMapper<E, D> {

    D toDto(E entity);

    E toEntity(D dto);
}
