package org.group3.mapper;

import org.group3.dto.request.RegisterRequestDto;
import org.group3.dto.response.FindAllResponseDto;
import org.group3.dto.response.FindByIdRespoonseDto;
import org.group3.dto.response.RegisterResponseDto;
import org.group3.entity.Auth;
import org.group3.rabbitMq.model.SaveAuthModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {

    IAuthMapper INSTANCE= Mappers.getMapper(IAuthMapper.class);

    Auth registerRequestDtotoAuth(RegisterRequestDto dto);
    RegisterResponseDto authToRegisterResponseDto(Auth auth);
    FindAllResponseDto authToFindAllResponseDto(Auth auth);

    FindByIdRespoonseDto authToFindByIdResponseDto(Auth auth);



}
