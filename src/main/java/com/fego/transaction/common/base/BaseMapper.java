package com.fego.transaction.common.base;

import org.springframework.stereotype.Component;

@Component
public interface BaseMapper<M extends BaseModel, D extends BaseDto> {
    D domainToDto(M baseModel);

    M dtoToDomain(D baseDto);
}