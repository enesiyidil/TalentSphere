package org.group3.service;

import org.group3.dto.request.Company;
import org.group3.dto.request.PersonelSaveManagerRequestDto;
import org.group3.dto.request.PersonelSaveRequestDto;
import org.group3.dto.request.PersonelUpdateRequestDto;
import org.group3.dto.response.CommentFindAllByCompanyIdWithPersonalNameResponseDto;
import org.group3.dto.response.CompanyInformationResponseDto;
import org.group3.dto.response.GetInformationResponseDto;
import org.group3.dto.response.PersonelResponseDto;
import org.group3.exception.ErrorType;
import org.group3.exception.PersonelServiceException;
import org.group3.manager.IAuthManager;
import org.group3.manager.ICommentManager;
import org.group3.manager.ICompanyManager;
import org.group3.manager.IManagerServiceManager;
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

    private final IAuthManager authManager;
    private final ICompanyManager companyManager;

    private final IManagerServiceManager managerServiceManager;

    private final ICommentManager commentManager;

    public PersonelService(IPersonelRepository personelRepository, IAuthManager authManager, ICompanyManager companyManager, IManagerServiceManager managerServiceManager, ICommentManager commentManager) {
        this.personelRepository = personelRepository;
        this.authManager = authManager;
        this.companyManager = companyManager;
        this.managerServiceManager = managerServiceManager;
        this.commentManager = commentManager;
    }

    public PersonelResponseDto save(PersonelSaveRequestDto dto){
        Personel personel = IPersonelMapper.INSTANCE.saveRequestDtoToPersonel(dto);
        personel.setAuthId(authManager.personalSave(IPersonelMapper.INSTANCE.personalToRegisterRequestDto(personel)).getBody());
        personelRepository.save(personel);
        companyManager.addPersonal(personel.getCompanyId(), personel.getId());
        managerServiceManager.addPersonal(personel.getManagerId(), personel.getId());
        return IPersonelMapper.INSTANCE.personelToResponseDto(personel);
    }

    public PersonelResponseDto saveManager(PersonelSaveManagerRequestDto dto){
        Personel personel = IPersonelMapper.INSTANCE.saveManagerRequestDtoToPersonel(dto);
        personel.setShiftId(1L);
        personelRepository.save(personel);
        return IPersonelMapper.INSTANCE.personelToResponseDto(personel);
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

    public PersonelResponseDto updatePersonel(PersonelUpdateRequestDto dto) {
        Optional<Personel> optionalPersonel = personelRepository.findById(dto.getId());
        if (optionalPersonel.isPresent()) {
            Personel existingPersonel = optionalPersonel.get();
            IPersonelMapper.INSTANCE.updatePersonelFromDto(dto, existingPersonel);
            personelRepository.save(existingPersonel);
            return IPersonelMapper.INSTANCE.personelToResponseDto(existingPersonel);
        } else {
            throw new PersonelServiceException(ErrorType.USER_NOT_FOUND);
        }

    }


    public List<PersonelResponseDto> findAllByCompanyId(Long companyId) {
        List<Personel> personelList = personelRepository.findAllByCompanyId(companyId);
        return personelList.stream()
                .map(IPersonelMapper.INSTANCE::personelToResponseDto)
                .collect(Collectors.toList());
    }

    public List<Personel> findAllPersonalByCompanyId(Long companyId) {
        return personelRepository.findAllByCompanyId(companyId);
    }

    public List<PersonelResponseDto> findAllByManagerId(Long managerId) {
        List<Personel> personelList = personelRepository.findAllByManagerId(managerId);
        return personelList.stream()
                .map(IPersonelMapper.INSTANCE::personelToResponseDto)
                .collect(Collectors.toList());
    }

    public PersonelResponseDto findByAuthId(Long authId) {
        Optional<Personel> optionalPersonel = personelRepository.findByAuthId(authId);
        if (optionalPersonel.isPresent()){
            return PersonelResponseDto.builder()
                    .id(optionalPersonel.get().getId())
                    .companyId(optionalPersonel.get().getCompanyId())
                    .authId(optionalPersonel.get().getAuthId())
                    .name(optionalPersonel.get().getName())
                    .surname(optionalPersonel.get().getSurname())
                    .email(optionalPersonel.get().getEmail())
                    .phone(optionalPersonel.get().getPhone())
                    .title(optionalPersonel.get().getTitle())
                    .photo(optionalPersonel.get().getPhoto())
                    .salary(optionalPersonel.get().getSalary())
                    .createdDate(optionalPersonel.get().getCreatedDate())
                    .updatedDate(optionalPersonel.get().getUpdatedDate())
                    .build();
        }else {
            throw new PersonelServiceException(ErrorType.USER_NOT_FOUND);
        }
    }


    public void deletePersonelById(Long id) {
        Optional<Personel> optionalPersonel = personelRepository.findById(id);
        if (optionalPersonel.isPresent()) {
            personelRepository.deleteById(id);
        } else {
            throw new PersonelServiceException(ErrorType.USER_NOT_FOUND);
        }
    }

    public String findNameByPersonalId(Long id) {
        Optional<Personel> optionalPersonel =personelRepository.findById(id);
        if (optionalPersonel.isPresent()) {
            return optionalPersonel.get().getName();
        } else {
            throw new PersonelServiceException(ErrorType.USER_NOT_FOUND);
        }
    }


    public GetInformationResponseDto getInformation(Long id) {
        Optional<Personel> optionalPersonel =personelRepository.findById(id);
        if (optionalPersonel.isPresent()) {
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
                    .managerName(managerServiceManager.findNameById(optionalPersonel.get().getManagerId()).getBody())
                    .shift(company.getShifts().stream().filter(shift -> shift.getId() == optionalPersonel.get().getShiftId()).findFirst().get())
                    .comments(commentManager.findAllByCompanyId(company.getId()).getBody().stream()
                            .map(comment -> CommentFindAllByCompanyIdWithPersonalNameResponseDto.builder()
                                    .content(comment.getContent())
                                    .createdDate(comment.getCreatedDate())
                                    .personalName(personelRepository.findById(comment.getPersonalId()).get().getName())
                                    .build()).collect(Collectors.toList()))

                    .build();
        } else {
            throw new PersonelServiceException(ErrorType.USER_NOT_FOUND);
        }

    }
}
