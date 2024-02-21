package org.group3.mapper;

import org.group3.dto.request.SaveRequestDto;
import org.group3.dto.response.VisitorFindAllResponseDto;
import org.group3.dto.response.FindByIdResponseDto;
import org.group3.entity.Visitor;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IVisitorMapper {

    IVisitorMapper INSTANCE = Mappers.getMapper(IVisitorMapper.class);

    Visitor saveRequestDtoToVisitor(SaveRequestDto dto);

    FindByIdResponseDto visitorToFindByIdResponseDto(Visitor visitor);

    VisitorFindAllResponseDto visitorToFindAllResponseDto(Visitor visitor);
}
