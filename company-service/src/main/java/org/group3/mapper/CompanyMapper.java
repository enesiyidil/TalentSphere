package org.group3.mapper;

import org.group3.dto.request.CompanySaveRequestDto;
import org.group3.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyMapper {

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    Company saveRequestDtoToCompany(CompanySaveRequestDto dto);
}
