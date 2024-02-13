package org.group3.service;

import org.group3.dto.request.PersonelSaveRequestDto;
import org.group3.dto.request.PersonelUpdateRequestDto;
import org.group3.dto.response.PersonelResponseDto;
import org.group3.exception.ErrorType;
import org.group3.exception.PersonelServiceException;
import org.group3.mapper.IPersonelMapper;
import org.group3.repository.IPersonelRepository;
import org.group3.repository.entity.Personel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonelService {
    private final IPersonelRepository personelRepository;

    public PersonelService(IPersonelRepository personelRepository) {
        this.personelRepository = personelRepository;
    }

    public PersonelResponseDto save(PersonelSaveRequestDto dto){
        return IPersonelMapper.INSTANCE.personelToResponseDto(personelRepository.save(IPersonelMapper.INSTANCE.saveRequestDtoToPersonel(dto)));
    }

//    public PersonelResponseDto findById(Long id){
//        Optional<Personel> optionalPersonel=personelRepository.findById(id);
//        if(optionalPersonel.isPresent()){
//            return IPersonellMapper.INSTANCE.personelToResponseDto(optionalPersonel.get());
//        }
//        throw new RuntimeException();//Todo:throw exception
//
//    }
//
//    public List<PersonelResponseDto> findAll(){
//        return personelRepository.findAll().stream()
//                .map(IPersonellMapper.INSTANCE::personelToResponseDto).collect(Collectors.toList());
//
//    }

    public PersonelResponseDto findById(Long id) {
        Optional<Personel> optionalPersonel = personelRepository.findById(id);
        if (optionalPersonel.isPresent()) {
            return IPersonelMapper.INSTANCE.personelToResponseDto(optionalPersonel.get());
        }
        throw new PersonelServiceException(ErrorType.USER_NOT_FOUND);
    }

    public List<PersonelResponseDto> findAll() {
        List<Personel> personelList = personelRepository.findAll();
        return personelList.stream()
                .map(IPersonelMapper.INSTANCE::personelToResponseDto)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        if (!personelRepository.existsById(id)) {
            throw new PersonelServiceException(ErrorType.USER_NOT_FOUND);
        }
        personelRepository.deleteById(id);
    }

    public PersonelResponseDto updatePersonel(Long id, PersonelUpdateRequestDto dto) {
        Optional<Personel> optionalPersonel = personelRepository.findById(id);
        if (optionalPersonel.isPresent()) {
            Personel existingPersonel = optionalPersonel.get();
            IPersonelMapper.INSTANCE.updatePersonelFromDto(dto, existingPersonel);
            personelRepository.save(existingPersonel);
            return IPersonelMapper.INSTANCE.personelToResponseDto(existingPersonel);
        } else {
            throw new PersonelServiceException(ErrorType.USER_NOT_FOUND);
        }

    }


    public List<PersonelResponseDto> findByCompanyId(Long companyId) {
        List<Personel> personelList = personelRepository.findAllByCompanyId(companyId);
        return personelList.stream()
                .map(IPersonelMapper.INSTANCE::personelToResponseDto)
                .collect(Collectors.toList());
    }

    public List<PersonelResponseDto> findAllByManagerId(Long managerId) {
        List<Personel> personelList = personelRepository.findAllByManagerId(managerId);
        return personelList.stream()
                .map(IPersonelMapper.INSTANCE::personelToResponseDto)
                .collect(Collectors.toList());
    }

    public List<PersonelResponseDto> findByAuthId(Long authId) {
        List<Personel> personelList = personelRepository.findByAuthId(authId);
        return personelList.stream()
                .map(IPersonelMapper.INSTANCE::personelToResponseDto)
                .collect(Collectors.toList());
    }


    public void deletePersonelById(Long id) {
        Optional<Personel> optionalPersonel = personelRepository.findById(id);
        if (optionalPersonel.isPresent()) {
            personelRepository.deleteById(id);
        } else {
            throw new PersonelServiceException(ErrorType.USER_NOT_FOUND);
        }
    }

       }
