package org.group3.mapper;

import org.group3.dto.request.ShiftSaveRequestDto;
import org.group3.dto.response.ShiftResponseDto;
import org.group3.entity.Shift;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShiftMapper {

    ShiftMapper INSTANCE = Mappers.getMapper(ShiftMapper.class);

    Shift saveRequestDtoToShift(ShiftSaveRequestDto dto);

    ShiftResponseDto shiftToResponseDto(Shift shift);
}
