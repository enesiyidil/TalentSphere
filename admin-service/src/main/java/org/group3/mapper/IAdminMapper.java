package org.group3.mapper;

import org.group3.dto.request.SaveRequestDto;
import org.group3.dto.response.FindAllResponseDto;
import org.group3.dto.response.FindByIdResponseDto;
import org.group3.entity.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAdminMapper {

    IAdminMapper INSTANCE = Mappers.getMapper(IAdminMapper.class);

    Admin saveRequestDtoToAdmin(SaveRequestDto dto);

    FindByIdResponseDto adminToFindByIdResponseDto(Admin admin);

    FindAllResponseDto adminToFindAllResponseDto(Admin admin);
}
