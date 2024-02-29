package org.group3.mapper;

import org.group3.dto.request.HolidayRequestDto;
import org.group3.dto.request.HolidaySaveByPersonalRequestDto;
import org.group3.dto.response.HolidayResponseDto;
import org.group3.dto.response.HolidayFindAllByCompanyIdAndStatusPendingResponseDto;
import org.group3.entity.Holiday;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HolidayMapper {

    HolidayMapper INSTANCE = Mappers.getMapper(HolidayMapper.class);

    Holiday saveRequestDtoToHoliday(HolidayRequestDto dto);

    HolidayResponseDto holidayToResponseDto(Holiday holiday);

    HolidayFindAllByCompanyIdAndStatusPendingResponseDto holidayToHolidayFindAllByCompanyIdAndStatusPendingResponseDto(Holiday holiday);

    Holiday holidaySaveByPersonalRequestDtoToHoliday(HolidaySaveByPersonalRequestDto dto);
}
