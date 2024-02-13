package org.group3.mapper;

import org.group3.dto.request.BreakSaveRequestDto;
import org.group3.dto.response.BreakResponseDto;
import org.group3.entity.Break;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BreakMapper {

    BreakMapper INSTANCE = Mappers.getMapper(BreakMapper.class);

    Break saveRequestDtoToBreak(BreakSaveRequestDto dto);

    BreakResponseDto breakToResponseDto(Break breakEntity);
}
