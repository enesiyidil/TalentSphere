package org.group3.mapper;

import org.group3.dto.request.AdminSaveRequestDto;
import org.group3.dto.request.ManagerOrPersonalSaveRequestDto;
import org.group3.dto.request.RegisterRequestDto;
import org.group3.dto.response.FindAllResponseDto;
import org.group3.dto.response.FindByIdResponseDto;
import org.group3.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {
    IAuthMapper INSTANCE= Mappers.getMapper(IAuthMapper.class);
    Auth registerRequestDtotoAuth(RegisterRequestDto dto);
    FindAllResponseDto authToFindAllResponseDto(Auth auth);
    FindByIdResponseDto authToFindByIdResponseDto(Auth auth);
    Auth managerOrPersonalSaveRequestDtoToAuth(ManagerOrPersonalSaveRequestDto dto);
    Auth adminSaveRequestDtoToAuth(AdminSaveRequestDto dto);
}
