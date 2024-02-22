package org.group3.service;

import org.group3.dto.request.ManagerSaveRequestDto;
import org.group3.dto.request.ManagerUpdateRequestDto;
import org.group3.dto.request.PersonelSaveManagerRequestDto;
import org.group3.dto.response.*;
import org.group3.entity.Manager;
import org.group3.entity.enums.EStatus;
import org.group3.exception.ErrorType;
import org.group3.exception.ManagerServiceException;
import org.group3.manager.IAuthManager;
import org.group3.manager.ICompanyManager;
import org.group3.manager.IPaymentManager;
import org.group3.manager.IPersonelManager;
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

    private final IPersonelManager personelManager;

    private final ICompanyManager companyManager;

    private final IPaymentManager paymentManager;

    private final CompanyAssignManagerProducer companyAssignManagerProducer;

    public ManagerService(ManagerRepository repository, AuthDeleteProducer authDeleteProducer, AuthUpdateProducer authUpdateProducer, IAuthManager authManager, IPersonelManager personelManager, ICompanyManager companyManager, IPaymentManager paymentManager, CompanyAssignManagerProducer companyAssignManagerProducer) {
        this.repository = repository;
        this.authDeleteProducer = authDeleteProducer;
        this.authUpdateProducer = authUpdateProducer;
        this.authManager = authManager;
        this.personelManager = personelManager;
        this.companyManager = companyManager;
        this.paymentManager = paymentManager;
        this.companyAssignManagerProducer = companyAssignManagerProducer;
    }

    public Boolean save(ManagerSaveRequestDto dto) {
        Manager manager = Manager.builder()
                .companyId(dto.getCompanyId())
                .email(dto.getEmail())
                .name(dto.getName())
                .surname(dto.getSurname())
                .authId(authManager.managerSave(dto).getBody())
                .build();
        System.out.println(dto.getName()+dto.getSurname()+dto.getEmail()+dto.getCompanyId());
        manager.setStatus(EStatus.ACTIVE);
        //authManager.managerSave(dto);

        repository.save(manager);
        companyAssignManagerProducer.assignManager(AssignManagerModel.builder()
                        .managerId(manager.getId())
                        .companyId(manager.getCompanyId())
                .build());
        personelManager.saveManager(PersonelSaveManagerRequestDto.builder()
                        .companyId(manager.getCompanyId())
                        .authId(manager.getAuthId())
                        .name(manager.getName())
                        .surname(manager.getSurname())
                        .email(manager.getEmail())
                        .phone(manager.getPhone())
                        .title(manager.getTitle())
                        .photo(manager.getPhoto())
                .build());
        return true;
    }

    public ManagerResponseDto findById(Long id) {
        Optional<Manager> optionalManager = repository.findById(id);
        if (optionalManager.isPresent()) {
            if (optionalManager.get().getStatus() == EStatus.DELETED)
                throw new ManagerServiceException(ErrorType.MANAGER_NOT_ACTIVE);
            return ManagerMapper.INSTANCE.ManagerToResponseDto(optionalManager.get());
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
            if (dto.getPhone() != null) {
                existingManager.setPhoto(dto.getPhoto());
            }
            if (dto.getTitle() != null) {
                existingManager.setTitle(dto.getTitle());
            }
            if (dto.getPhone() != null) {
                existingManager.setPhone(dto.getPhone());
            }
            existingManager.setUpdatedDateTime(LocalDateTime.now());
            existingManager = repository.save(existingManager);
            if (dto.getEmail() != null) {
                authUpdateProducer.update(AuthUpdateModel.builder()
                        .email(dto.getEmail())
                        .authId(existingManager.getAuthId())
                        .phone(dto.getPhone())
                        .build());
            }
            return ManagerMapper.INSTANCE.ManagerToResponseDto(existingManager);
        }
        throw new ManagerServiceException(ErrorType.MANAGER_NOT_FOUND);
    }

    public ManagerResponseDto findByAuthId(Long authId) {
        Optional<Manager> optionalManager = repository.findByAuthId(authId);
        if (optionalManager.isPresent()) {
            if (optionalManager.get().getStatus() == EStatus.DELETED)
                throw new ManagerServiceException(ErrorType.MANAGER_NOT_ACTIVE);
            return ManagerMapper.INSTANCE.ManagerToResponseDto(optionalManager.get());
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
                    //manager.getCompanies().add(model.getCompanyId());
                    manager.setCompanyId(model.getCompanyId());
                    repository.save(manager);
                }
        );
        optionalExistingManager.orElseThrow(
                () -> new ManagerServiceException(ErrorType.MANAGER_NOT_FOUND)
        );
    }

    public void deleteCompany(CompanyModel model) {
        Optional<Manager> optionalExistingManager = repository.findById(model.getManagerId());
        optionalExistingManager.ifPresent(
                manager -> {
                    if (optionalExistingManager.get().getStatus() == EStatus.DELETED)
                        throw new ManagerServiceException(ErrorType.MANAGER_NOT_ACTIVE);
                    if (manager.getCompanyId().equals(model.getCompanyId())) {
                        //manager.setCompanyId(manager.getCompanyId().(model.getCompanyId()));
                    } else {
                        throw new ManagerServiceException(ErrorType.COMPANY_NOT_REGISTERED);
                    }
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
        return repository.findAll().stream().map(ManagerMapper.INSTANCE::ManagerToResponseDto).collect(Collectors.toList());
    }

    public List<Integer> getInfoForVisitor(Long id) {
        Optional<Manager> optionalManager = repository.findById(id);
        List<Integer> info=new ArrayList<>();
        //info.add(optionalManager.get().getCompanies().size());
        info.add(optionalManager.get().getPersonals().size());
        return info;
    }

    public GetInformationResponseDto getInformation(Long id) {
        Company company = companyManager.findByManagerId(id).getBody();
        System.out.println(company);
        assert company != null;
        List<Payment> payments = paymentManager.findAllByCompanyId(company.getId()).getBody();
        List<Personel> personels =personelManager.findAllPersonalByCompanyId(company.getId()).getBody();
        return GetInformationResponseDto.builder()
                .id(company.getId())
                .managerId(company.getManagerId())
                .name(company.getName())
                .address(company.getAddress())
                .gallery(company.getGallery())
                .payments(payments)
                .personals(personels)
                .communications(company.getCommunications())
                .holidays(company.getHolidays())
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
}
