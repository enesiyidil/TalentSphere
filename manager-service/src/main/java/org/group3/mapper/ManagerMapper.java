package org.group3.mapper;

import org.group3.dto.response.ManagerResponseDto;
import org.group3.entity.Manager;
import org.group3.rabbit.model.ManagerSaveModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ManagerMapper {

    ManagerMapper INSTANCE = Mappers.getMapper(ManagerMapper.class);

    Manager saveModelToManager(ManagerSaveModel model);

    ManagerResponseDto ManagerToResponseDto(Manager manager);
}
