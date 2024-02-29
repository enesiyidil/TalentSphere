package org.group3.service;

import org.group3.dto.request.*;
import org.group3.dto.response.CommentFindAllByCompanyIdWithPersonalNameResponseDto;
import org.group3.dto.response.CompanyInformationResponseDto;
import org.group3.dto.response.GetInformationResponseDto;
import org.group3.dto.response.PersonalResponseDto;
import org.group3.exception.ErrorType;
import org.group3.exception.PersonelServiceException;
import org.group3.manager.IAuthManager;
import org.group3.manager.ICommentManager;
import org.group3.manager.ICompanyManager;
import org.group3.manager.IManagerServiceManager;
import org.group3.mapper.IPersonelMapper;
import org.group3.rabbit.model.AuthUpdateModel;
import org.group3.rabbit.producer.AuthUpdateProducer;
import org.group3.repository.IPersonalRepository;
import org.group3.repository.entity.Personal;
import org.group3.repository.entity.enums.ERole;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonalService {
    private final IPersonalRepository personalRepository;
    private final IAuthManager authManager;
    private final ICompanyManager companyManager;
    private final IManagerServiceManager managerServiceManager;
    private final ICommentManager commentManager;
    private final AuthUpdateProducer authUpdateProducer;

    public PersonalService(IPersonalRepository personalRepository, IAuthManager authManager, ICompanyManager companyManager, IManagerServiceManager managerServiceManager, ICommentManager commentManager, AuthUpdateProducer authUpdateProducer) {
        this.personalRepository = personalRepository;
        this.authManager = authManager;
        this.companyManager = companyManager;
        this.managerServiceManager = managerServiceManager;
        this.commentManager = commentManager;
        this.authUpdateProducer = authUpdateProducer;
    }

    public PersonalResponseDto save(PersonalSaveRequestDto dto){
        Personal personal = IPersonelMapper.INSTANCE.saveRequestDtoToPersonel(dto);
        ManagerOrPersonalSaveRequestDto dto1 = IPersonelMapper.INSTANCE.personalToManagerOrPersonalSaveRequestDto(personal);
        dto1.setRole(ERole.PERSONAL);
        personal.setAuthId(authManager.personalSave(dto1).getBody());
        personal.setUpdatedDate(LocalDateTime.now().toString());
        personalRepository.save(personal);
        companyManager.addPersonal(personal.getCompanyId(), personal.getId());
        managerServiceManager.addPersonal(personal.getManagerId(), personal.getId());
        return IPersonelMapper.INSTANCE.personelToResponseDto(personal);

    }

    public PersonalResponseDto saveManager(PersonalSaveManagerRequestDto dto){
        Personal personal = IPersonelMapper.INSTANCE.saveManagerRequestDtoToPersonel(dto);
        personalRepository.save(personal);
        return IPersonelMapper.INSTANCE.personelToResponseDto(personal);
    }
    public PersonalResponseDto findById(Long id) {
        Optional<Personal> optionalPersonal = personalRepository.findById(id);
        if (optionalPersonal.isPresent()) {
            return IPersonelMapper.INSTANCE.personelToResponseDto(optionalPersonal.get());
        }
        throw new PersonelServiceException(ErrorType.USER_NOT_FOUND);
    }

    public List<PersonalResponseDto> findAll() {
        List<Personal> personalList = personalRepository.findAll();
        return personalList.stream()
                .map(IPersonelMapper.INSTANCE::personelToResponseDto)
                .collect(Collectors.toList());
    }
    public PersonalResponseDto updatePersonal(PersonalUpdateRequestDto dto) {
        Optional<Personal> optionalPersonal = personalRepository.findById(dto.getId());
        if (optionalPersonal.isPresent()) {
            Personal existingPersonal = optionalPersonal.get();
            if (dto.getName() != null) {
                existingPersonal.setName(dto.getName());
            }
            if (dto.getSurname() != null) {
                existingPersonal.setSurname(dto.getSurname());
            }
            if (dto.getEmail() != null) {
                existingPersonal.setEmail(dto.getEmail());
            }
            if (dto.getPhoto() != null) {
                existingPersonal.setPhoto(dto.getPhoto());
            }
            if (dto.getTitle() != null) {
                existingPersonal.setTitle(dto.getTitle());
            }
            if (dto.getPhone() != null) {
                existingPersonal.setPhone(dto.getPhone());
            }
            if (dto.getSalary() != null) {
                existingPersonal.setSalary(dto.getSalary());
            }
            if (dto.getGender() != null) {
                existingPersonal.setGender(dto.getGender());
            }

            existingPersonal.setUpdatedDate(LocalDateTime.now().toString());
            personalRepository.save(existingPersonal);
            if (dto.getEmail() != null || dto.getPhone() != null) {
                authUpdateProducer.update(AuthUpdateModel.builder()
                        .email(dto.getEmail())
                        .authId(existingPersonal.getAuthId())
                        .phone(dto.getPhone())
                        .build());

            }
            return IPersonelMapper.INSTANCE.personelToResponseDto(existingPersonal);
        } else {
            throw new PersonelServiceException(ErrorType.USER_NOT_FOUND);
        }

    }

    public List<PersonalResponseDto> findAllByCompanyId(Long companyId) {
        List<Personal> personalList = personalRepository.findAllByCompanyId(companyId);
        return personalList.stream()
                .map(IPersonelMapper.INSTANCE::personelToResponseDto)
                .collect(Collectors.toList());
    }

    public List<Personal> findAllPersonalByCompanyId(Long companyId) {
        return personalRepository.findAllByCompanyId(companyId);
    }

    public List<PersonalResponseDto> findAllByManagerId(Long managerId) {
        List<Personal> personalList = personalRepository.findAllByManagerId(managerId);
        return personalList.stream()
                .map(IPersonelMapper.INSTANCE::personelToResponseDto)
                .collect(Collectors.toList());
    }

    public PersonalResponseDto findByAuthId(Long authId) {
        Optional<Personal> optionalPersonal = personalRepository.findByAuthId(authId);
        if (optionalPersonal.isPresent()){
            return PersonalResponseDto.builder()
                    .id(optionalPersonal.get().getId())
                    .companyId(optionalPersonal.get().getCompanyId())
                    .authId(optionalPersonal.get().getAuthId())
                    .name(optionalPersonal.get().getName())
                    .surname(optionalPersonal.get().getSurname())
                    .email(optionalPersonal.get().getEmail())
                    .phone(optionalPersonal.get().getPhone())
                    .title(optionalPersonal.get().getTitle())
                    .photo(optionalPersonal.get().getPhoto())
                    .salary(optionalPersonal.get().getSalary())
                    .gender(optionalPersonal.get().getGender())
                    .createdDate(optionalPersonal.get().getCreatedDate())
                    .updatedDate(optionalPersonal.get().getUpdatedDate())
                    .build();
        }else {
            throw new PersonelServiceException(ErrorType.USER_NOT_FOUND);
        }
    }


    public void deletePersonalById(Long id) {
        Optional<Personal> optionalPersonal = personalRepository.findById(id);
        if (optionalPersonal.isPresent()) {
            personalRepository.deleteById(id);
        } else {
            throw new PersonelServiceException(ErrorType.USER_NOT_FOUND);
        }
    }

    public String findNameByPersonalId(Long id) {
        Optional<Personal> optionalPersonal = personalRepository.findById(id);
        if (optionalPersonal.isPresent()) {
            return optionalPersonal.get().getName();
        } else {
            throw new PersonelServiceException(ErrorType.USER_NOT_FOUND);
        }
    }

    public String findNameByPersonalAuthId(Long authId) {
        Optional<Personal> optionalPersonal = personalRepository.findByAuthId(authId);
        if (optionalPersonal.isPresent()) {
            return optionalPersonal.get().getName();
        } else {
            throw new PersonelServiceException(ErrorType.USER_NOT_FOUND);
        }
    }


    public GetInformationResponseDto getInformation(Long id) {
        Optional<Personal> optionalPersonal = personalRepository.findById(id);
        if (optionalPersonal.isPresent()) {
            Company company = companyManager.findByPersonalIdGetInfo(id).getBody();
            assert company != null;
            return GetInformationResponseDto.builder()
                    .company(CompanyInformationResponseDto.builder()
                            .name(company.getName())
                            .address(company.getAddress())
                            .gallery(company.getGallery())
                            .personalNumber(company.getPersonalNumber())
                            .communications(company.getCommunications())
                            .build())
                    .managerName(managerServiceManager.findNameById(optionalPersonal.get().getManagerId()).getBody())
                    .shift(company.getShifts().stream().filter(shift -> Objects.equals(shift.getId(), optionalPersonal.get().getShiftId())).findFirst()
                            .orElseThrow(() -> new PersonelServiceException(ErrorType.USER_NOT_FOUND)))
                    .comments(Objects.requireNonNull(commentManager.findAllByCompanyId(company.getId()).getBody()).stream()
                            .map(comment -> CommentFindAllByCompanyIdWithPersonalNameResponseDto.builder()
                                    .content(comment.getContent())
                                    .createdDate(comment.getCreatedDate())
                                    .personalName(personalRepository.findById(comment.getPersonalId())
                                            .orElseThrow(() -> new PersonelServiceException(ErrorType.USER_NOT_FOUND)).getName())
                                    .build()).collect(Collectors.toList()))

                    .build();
        } else {
            throw new PersonelServiceException(ErrorType.USER_NOT_FOUND);
        }

    }


}
