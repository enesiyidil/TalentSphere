package org.group3.service;

import org.group3.dto.request.ManagerUpdateRequestDto;
import org.group3.dto.response.ManagerResponseDto;
import org.group3.entity.Manager;
import org.group3.entity.enums.EStatus;
import org.group3.exception.ErrorType;
import org.group3.exception.ManagerServiceException;
import org.group3.mapper.ManagerMapper;
import org.group3.rabbit.model.AuthUpdateModel;
import org.group3.rabbit.model.CompanyModel;
import org.group3.rabbit.model.ManagerSaveModel;
import org.group3.rabbit.model.PersonalModel;
import org.group3.rabbit.producer.AuthDeleteProducer;
import org.group3.rabbit.producer.AuthUpdateProducer;
import org.group3.repository.ManagerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ManagerService {

    private final ManagerRepository repository;

    private final AuthDeleteProducer authDeleteProducer;

    private final AuthUpdateProducer authUpdateProducer;

    public ManagerService(ManagerRepository repository, AuthDeleteProducer authDeleteProducer, AuthUpdateProducer authUpdateProducer) {
        this.repository = repository;
        this.authDeleteProducer = authDeleteProducer;
        this.authUpdateProducer = authUpdateProducer;
    }

    public void save(ManagerSaveModel model) {
        repository.save(Manager.builder()
                        .authId(model.getAuthId())
                        .email(model.getEmail())
                .build());
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
                    if (manager.getCompanies().contains(model.getCompanyId())) {
                        throw new ManagerServiceException(ErrorType.COMPANY_ALREADY_EXISTS);
                    }
                    manager.getCompanies().add(model.getCompanyId());
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
                    if (manager.getCompanies().contains(model.getCompanyId())) {
                        manager.getCompanies().remove(model.getCompanyId());
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
}
