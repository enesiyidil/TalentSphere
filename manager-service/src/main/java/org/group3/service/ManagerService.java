package org.group3.service;

import org.group3.dto.request.ManagerSaveRequestDto;
import org.group3.dto.request.ManagerUpdateRequestDto;
import org.group3.dto.request.PersonalSaveManagerRequestDto;
import org.group3.dto.response.*;
import org.group3.entity.Manager;
import org.group3.entity.enums.ERole;
import org.group3.entity.enums.EStatus;
import org.group3.exception.ErrorType;
import org.group3.exception.ManagerServiceException;
import org.group3.manager.*;
import org.group3.mapper.ManagerMapper;
import org.group3.rabbit.model.AssignManagerModel;
import org.group3.rabbit.model.AuthUpdateModel;
import org.group3.rabbit.model.CompanyModel;
import org.group3.rabbit.model.PersonalModel;
import org.group3.rabbit.producer.AuthDeleteProducer;
import org.group3.rabbit.producer.AuthUpdateProducer;
import org.group3.rabbit.producer.CompanyAssignManagerProducer;
import org.group3.repository.ManagerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManagerService {

    private final ManagerRepository repository;
    private final AuthDeleteProducer authDeleteProducer;
    private final AuthUpdateProducer authUpdateProducer;
    private final IAuthManager authManager;
    private final IPersonalManager personalManager;
    private final ICompanyManager companyManager;
    private final IPaymentManager paymentManager;
    private final CompanyAssignManagerProducer companyAssignManagerProducer;

    private final IHolidayManager holidayManager;

    public ManagerService(ManagerRepository repository,
                          AuthDeleteProducer authDeleteProducer,
                          AuthUpdateProducer authUpdateProducer,
                          IAuthManager authManager,
                          IPersonalManager personalManager,
                          ICompanyManager companyManager,
                          IPaymentManager paymentManager,
                          CompanyAssignManagerProducer companyAssignManagerProducer, IHolidayManager holidayManager) {
        this.repository = repository;
        this.authDeleteProducer = authDeleteProducer;
        this.authUpdateProducer = authUpdateProducer;
        this.authManager = authManager;
        this.personalManager = personalManager;
        this.companyManager = companyManager;
        this.paymentManager = paymentManager;
        this.companyAssignManagerProducer = companyAssignManagerProducer;
        this.holidayManager = holidayManager;
    }

    @Transactional
    public Boolean save(ManagerSaveRequestDto dto) {
        dto.setRole(ERole.MANAGER);
        Manager manager = Manager.builder()
                .companyId(dto.getCompanyId())
                .email(dto.getEmail())
                .name(dto.getName())
                .surname(dto.getSurname())
                .authId(authManager.managerSave(dto).getBody())
                .photo(dto.getPhoto())
                .title(dto.getTitle())
                .gender(dto.getGender())
                .phone(dto.getPhone())
                .updatedDateTime(LocalDateTime.now().toString())
                .ePackage(dto.getEPackage())
                .build();
        System.out.println(dto.getName()+dto.getSurname() + dto.getEmail() + dto.getCompanyId());
        manager.setStatus(EStatus.ACTIVE);

        repository.save(manager);
        companyAssignManagerProducer.assignManager(AssignManagerModel.builder()
                        .managerId(manager.getId())
                        .companyId(manager.getCompanyId())
                .build());
        personalManager.saveManager(PersonalSaveManagerRequestDto.builder()
                        .companyId(manager.getCompanyId())
                        .authId(manager.getAuthId())
                        .name(manager.getName())
                        .surname(manager.getSurname())
                        .email(manager.getEmail())
                        .phone(manager.getPhone())
                        .title(manager.getTitle())
                        .photo(manager.getPhoto())
                        .gender(manager.getGender())
                        .salary(dto.getSalary())
                        .shiftId(dto.getShiftId())
                .build());
        return true;
    }

    public ManagerResponseDto findById(Long id) {
        Optional<Manager> optionalManager = repository.findById(id);
        if (optionalManager.isPresent()) {
            if (optionalManager.get().getStatus() == EStatus.DELETED)
                throw new ManagerServiceException(ErrorType.MANAGER_NOT_ACTIVE);
            ManagerResponseDto dto = ManagerMapper.INSTANCE.managerToManagerResponseDto(optionalManager.get());
            dto.setEPackage(optionalManager.get().getEPackage());
            return dto;
        }
        throw new ManagerServiceException(ErrorType.MANAGER_NOT_FOUND);
    }

    public Boolean deleteById(Long id) {
        Optional<Manager> optionalManager = repository.findById(id);
        if (optionalManager.isPresent()) {
            if (optionalManager.get().getStatus() == EStatus.DELETED)
                throw new ManagerServiceException(ErrorType.MANAGER_NOT_ACTIVE);
            optionalManager.get().setStatus(EStatus.DELETED);
            repository.save(optionalManager.get());
            authDeleteProducer.delete(optionalManager.get().getAuthId());
            return true;
        }
        throw new ManagerServiceException(ErrorType.MANAGER_NOT_FOUND);
    }

    public ManagerResponseDto update(ManagerUpdateRequestDto dto) {
        Optional<Manager> optionalExistingManager = repository.findById(dto.getId());
        if ((optionalExistingManager.isPresent())) {
            if (optionalExistingManager.get().getStatus() == EStatus.DELETED)
                throw new ManagerServiceException(ErrorType.MANAGER_NOT_ACTIVE);
            Manager existingManager = optionalExistingManager.get();
            if (dto.getName() != null) {
                existingManager.setName(dto.getName());
            }
            if (dto.getSurname() != null) {
                existingManager.setSurname(dto.getSurname());
            }
            if (dto.getEmail() != null) {
                existingManager.setEmail(dto.getEmail());
            }
            if (dto.getPhoto() != null) {
                existingManager.setPhoto(dto.getPhoto());
            }
            if (dto.getTitle() != null) {
                existingManager.setTitle(dto.getTitle());
            }
            if (dto.getPhone() != null) {
                existingManager.setPhone(dto.getPhone());
            }
            if (dto.getPacket() != null) {
                existingManager.setEPackage(dto.getPacket());

            }
            if (dto.getGender() != null) {
                existingManager.setGender(dto.getGender());

            }
            existingManager.setUpdatedDateTime(LocalDateTime.now().toString());
            existingManager = repository.save(existingManager);
            if (dto.getEmail() != null || dto.getPhone() != null) {
                authUpdateProducer.update(AuthUpdateModel.builder()
                        .email(dto.getEmail())
                        .authId(existingManager.getAuthId())
                        .phone(dto.getPhone())
                        .build());

            }
            ManagerResponseDto dto2 = ManagerMapper.INSTANCE.managerToManagerResponseDto(existingManager);
            dto2.setEPackage(existingManager.getEPackage());
            return dto2;
        }
        throw new ManagerServiceException(ErrorType.MANAGER_NOT_FOUND);
    }

    public ManagerResponseDto findByAuthId(Long authId) {
        Optional<Manager> optionalManager = repository.findByAuthId(authId);
        if (optionalManager.isPresent()) {
            if (optionalManager.get().getStatus() == EStatus.DELETED)
                throw new ManagerServiceException(ErrorType.MANAGER_NOT_ACTIVE);
            ManagerResponseDto dto = ManagerMapper.INSTANCE.managerToManagerResponseDto(optionalManager.get());
            dto.setEPackage(optionalManager.get().getEPackage());
            return dto;
        }
        throw new ManagerServiceException(ErrorType.MANAGER_NOT_FOUND);
    }

    public void addCompany(CompanyModel model) {
        Optional<Manager> optionalExistingManager = repository.findById(model.getManagerId());
        optionalExistingManager.ifPresent(
                manager -> {
                    if (optionalExistingManager.get().getStatus() == EStatus.DELETED)
                        throw new ManagerServiceException(ErrorType.MANAGER_NOT_ACTIVE);
                    if (manager.getCompanyId()!=null && manager.getCompanyId().equals(model.getCompanyId())) {
                        throw new ManagerServiceException(ErrorType.COMPANY_ALREADY_EXISTS);
                    }
                    manager.setCompanyId(model.getCompanyId());
                    repository.save(manager);
                }
        );
        optionalExistingManager.orElseThrow(
                () -> new ManagerServiceException(ErrorType.MANAGER_NOT_FOUND)
        );
    }
    public void addPersonal(PersonalModel model) {
        Optional<Manager> optionalExistingManager = repository.findById(model.getManagerId());
        optionalExistingManager.ifPresent(
                manager -> {
                    if (optionalExistingManager.get().getStatus() == EStatus.DELETED)
                        throw new ManagerServiceException(ErrorType.MANAGER_NOT_ACTIVE);
                    if (manager.getPersonals().contains(model.getPersonalId())) {
                        throw new ManagerServiceException(ErrorType.PERSONAL_ALREADY_EXISTS);
                    }
                    manager.getPersonals().add(model.getPersonalId());
                }
        );
        optionalExistingManager.orElseThrow(
                () -> new ManagerServiceException(ErrorType.MANAGER_NOT_FOUND)
        );
    }

    public void addPersonal(Long id, Long personId) {
        Optional<Manager> optionalExistingManager = repository.findById(id);
        optionalExistingManager.ifPresent(
                manager -> {
                    if (optionalExistingManager.get().getStatus() == EStatus.DELETED)
                        throw new ManagerServiceException(ErrorType.MANAGER_NOT_ACTIVE);
                    if (manager.getPersonals().contains(personId)) {
                        throw new ManagerServiceException(ErrorType.PERSONAL_ALREADY_EXISTS);
                    }
                    manager.getPersonals().add(personId);
                }
        );
        optionalExistingManager.orElseThrow(
                () -> new ManagerServiceException(ErrorType.MANAGER_NOT_FOUND)
        );
    }

    public void deletePersonal(PersonalModel model) {
        Optional<Manager> optionalExistingManager = repository.findById(model.getManagerId());
        optionalExistingManager.ifPresent(
                manager -> {
                    if (optionalExistingManager.get().getStatus() == EStatus.DELETED)
                        throw new ManagerServiceException(ErrorType.MANAGER_NOT_ACTIVE);
                    if (manager.getPersonals().contains(model.getPersonalId())) {
                        manager.getPersonals().remove(model.getPersonalId());
                    } else {
                        throw new ManagerServiceException(ErrorType.PERSONAL_NOT_REGISTERED);
                    }
                }
        );
        optionalExistingManager.orElseThrow(
                () -> new ManagerServiceException(ErrorType.MANAGER_NOT_FOUND)
        );
    }

    public List<ManagerResponseDto> findAllDto() {
        return repository.findAll().stream().map((manager) -> {
            ManagerResponseDto dto = ManagerMapper.INSTANCE.managerToManagerResponseDto(manager);
            dto.setEPackage(manager.getEPackage());
            return dto;
        } ).collect(Collectors.toList());
    }

    public List<Integer> getInfoForVisitor(Long id) {
        Optional<Manager> optionalManager = repository.findById(id);
        List<Integer> info=new ArrayList<>();
        info.add(optionalManager.orElseThrow(()-> new ManagerServiceException(ErrorType.MANAGER_NOT_FOUND)).getPersonals().size());
        return info;
    }

    public GetInformationResponseDto getInformation(Long id) {
        Company company = companyManager.findByManagerId(id).getBody();
        System.out.println(company);
        assert company != null;
        List<Payment> payments = paymentManager.findAllByCompanyId(company.getId()).getBody();
        List<Personal> personals = personalManager.findAllPersonalByCompanyId(company.getId()).getBody();
        List<HolidayResponseDto> holidays = holidayManager.findAllByCompanyId(company.getId()).getBody();
        return GetInformationResponseDto.builder()
                .id(company.getId())
                .managerId(company.getManagerId())
                .name(company.getName())
                .address(company.getAddress())
                .gallery(company.getGallery())
                .payments(payments)
                .personals(personals)
                .communications(company.getCommunications())
                .holidays(holidays)
                .shifts(company.getShifts())
                .createdDateTime(company.getCreatedDateTime())
                .updatedDateTime(company.getUpdatedDateTime())
                .status(company.getStatus())
                .build();
    }

    public String findNameById(Long id) {
        Optional<Manager> optionalManager = repository.findById(id);
        if (optionalManager.isPresent()){
            return optionalManager.get().getName();
        }else {
            throw new ManagerServiceException(ErrorType.MANAGER_NOT_FOUND);
        }
    }

    public String findNameByAuthId(Long authId) {
        Optional<Manager> optionalManager = repository.findByAuthId(authId);
        if (optionalManager.isPresent()){
            return optionalManager.get().getName();
        }else {
            throw new ManagerServiceException(ErrorType.MANAGER_NOT_FOUND);
        }
    }
}
