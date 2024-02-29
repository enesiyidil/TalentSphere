package org.group3.mapper;

import org.group3.dto.request.ManagerOrPersonalSaveRequestDto;
import org.group3.dto.request.PersonalSaveManagerRequestDto;
import org.group3.dto.request.PersonalSaveRequestDto;
import org.group3.dto.request.PersonalUpdateRequestDto;
import org.group3.dto.response.PersonalResponseDto;
import org.group3.repository.entity.Personal;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IPersonelMapper {
    IPersonelMapper INSTANCE = Mappers.getMapper( IPersonelMapper.class);

    Personal saveRequestDtoToPersonel(PersonalSaveRequestDto dto);
    Personal saveManagerRequestDtoToPersonel(PersonalSaveManagerRequestDto dto);

    PersonalResponseDto personelToResponseDto(Personal personal);
//    @Mapping(target = "name", source = "dto.name")
//    @Mapping(target = "surname", source = "dto.surname")
//    @Mapping(target = "email", source = "dto.email")
//    @Mapping(target = "phone", source = "dto.phone")
//    @Mapping(target = "title", source = "dto.title")
//    @Mapping(target = "photo", source = "dto.photo")
//    @Mapping(target = "salary", source = "dto.salary")
//    Personel updatePersonelFromDto(Long id, PersonelUpdateRequestDto dto);


    ManagerOrPersonalSaveRequestDto personalToManagerOrPersonalSaveRequestDto(Personal personal);


    void updatePersonelFromDto(PersonalUpdateRequestDto dto, @MappingTarget Personal existingPersonal);
}
