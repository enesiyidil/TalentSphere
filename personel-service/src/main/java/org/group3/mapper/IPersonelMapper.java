package org.group3.mapper;

import org.group3.dto.request.PersonelSaveManagerRequestDto;
import org.group3.dto.request.PersonelSaveRequestDto;
import org.group3.dto.request.PersonelUpdateRequestDto;
import org.group3.dto.request.RegisterRequestDto;
import org.group3.dto.response.PersonelResponseDto;
import org.group3.repository.entity.Personel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IPersonelMapper {
    IPersonelMapper INSTANCE = Mappers.getMapper( IPersonelMapper.class);

    Personel saveRequestDtoToPersonel(PersonelSaveRequestDto dto);
    Personel saveManagerRequestDtoToPersonel(PersonelSaveManagerRequestDto dto);

    PersonelResponseDto personelToResponseDto(Personel personel);
//    @Mapping(target = "name", source = "dto.name")
//    @Mapping(target = "surname", source = "dto.surname")
//    @Mapping(target = "email", source = "dto.email")
//    @Mapping(target = "phone", source = "dto.phone")
//    @Mapping(target = "title", source = "dto.title")
//    @Mapping(target = "photo", source = "dto.photo")
//    @Mapping(target = "salary", source = "dto.salary")
//    Personel updatePersonelFromDto(Long id, PersonelUpdateRequestDto dto);


    RegisterRequestDto personalToRegisterRequestDto(Personel personel);


    void updatePersonelFromDto(PersonelUpdateRequestDto dto, @MappingTarget Personel existingPersonel);
}
