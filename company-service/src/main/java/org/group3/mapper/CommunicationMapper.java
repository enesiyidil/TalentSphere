package org.group3.mapper;

import org.group3.dto.request.PhoneRequestDto;
import org.group3.dto.response.PhoneResponseDto;
import org.group3.entity.Communication;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommunicationMapper {

    CommunicationMapper INSTANCE = Mappers.getMapper(CommunicationMapper.class);

    Communication saveRequestDtoToCommunication(PhoneRequestDto dto);

    PhoneResponseDto communicationToResponseDto(Communication communication);
}
