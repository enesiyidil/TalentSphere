package org.group3.service;

import org.group3.dto.request.SaveRequestDto;
import org.group3.dto.request.UpdateRequestDto;
import org.group3.dto.response.*;
import org.group3.entity.Admin;
import org.group3.entity.enums.EStatus;
import org.group3.exception.AdminManagerException;
import org.group3.exception.ErrorType;
import org.group3.manager.IManagerServiceManager;
import org.group3.manager.IPersonelManager;
import org.group3.mapper.IAdminMapper;
import org.group3.rabbitMq.model.DeleteAuthModel;
import org.group3.rabbitMq.model.UpdateAuthModel;
import org.group3.rabbitMq.producer.AuthDeleteProducer;
import org.group3.rabbitMq.producer.AuthUpdateProduce;
import org.group3.repository.AdminRepository;
import org.group3.utility.ServiceManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService extends ServiceManager<Admin, Long> {

    private final AdminRepository repository;

    private final AuthUpdateProduce authUpdateProduce;

    private final AuthDeleteProducer authDeleteProducer;

    private final IManagerServiceManager managerServiceManager;

    private final IPersonelManager personelManager;

    public AdminService(AdminRepository repository, AuthUpdateProduce authUpdateProduce, AuthDeleteProducer authDeleteProducer, IManagerServiceManager managerServiceManager, IPersonelManager personelManager) {
        super(repository);
        this.repository = repository;
        this.authUpdateProduce = authUpdateProduce;
        this.authDeleteProducer = authDeleteProducer;
        this.managerServiceManager = managerServiceManager;
        this.personelManager = personelManager;
    }

    public String saveDto(SaveRequestDto dto) {
        if (repository.existsByEmail(dto.getEmail()) || repository.existsByPhone(dto.getPhone())) {
            throw new AdminManagerException(ErrorType.EMAIL_OR_PHONE_EXITS);
        }
        Admin admin = IAdminMapper.INSTANCE.saveRequestDtoToAdmin(dto);
        //admin.setCreatedDate(System.currentTimeMillis());
        save(admin);
        return "kayıt işlemi başarılı";

    }

    public FindByIdResponseDto findByIdDto(Long id) {
        Optional<Admin> optionalAdmin = findById(id);
        if (optionalAdmin.isEmpty()) {
            throw new AdminManagerException(ErrorType.ID_NOT_FOUND);
        }
        return IAdminMapper.INSTANCE.adminToFindByIdResponseDto(optionalAdmin.get());
    }

    public FindByIdResponseDto findByAuthIdDto(Long authId) {
        Optional<Admin> optionalAdmin = repository.findByAuthId(authId);
        if (optionalAdmin.isEmpty()) {
            throw new AdminManagerException(ErrorType.ID_NOT_FOUND);
        }
        return IAdminMapper.INSTANCE.adminToFindByIdResponseDto(optionalAdmin.get());
    }


    public List<FindAllResponseDto> findAllDto() {
        return findAll().stream().map(IAdminMapper.INSTANCE::adminToFindAllResponseDto).collect(Collectors.toList());
    }

    public String softUpdate(UpdateRequestDto dto) {
        Optional<Admin> optionalAdmin = findById(dto.getId());
        Admin admin = optionalAdmin.orElseThrow(() -> new AdminManagerException(ErrorType.USER_NOT_FOUND));
        if (optionalAdmin.get().getStatus().equals(EStatus.DELETED)) {
            throw new AdminManagerException(ErrorType.USER_ALREADY_DELETED);
        }
        if (repository.existsByEmail(dto.getEmail()) || repository.existsByPhone(dto.getPhone())) {
            throw new AdminManagerException(ErrorType.EMAIL_OR_PHONE_EXITS);
        }

        if (dto.getEmail() != null) {
            admin.setEmail(dto.getEmail());
        }
        if (dto.getPhone() != null) {
            admin.setPhone(dto.getPhone());
        }
        admin.setUpdatedDate(System.currentTimeMillis());

        update(admin);
        authUpdateProduce.convertAndSend(UpdateAuthModel.builder()
                .authid(admin.getAuthId())
                .email(admin.getEmail())
                .build());

        return "başarılı";
    }

    public String softDelete(Long id) {
        Optional<Admin> optionalAdmin = findById(id);
        if (optionalAdmin.isEmpty()) {
            throw new AdminManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (optionalAdmin.get().getStatus().equals(EStatus.DELETED)) {
            throw new AdminManagerException(ErrorType.USER_ALREADY_DELETED);
        }
        optionalAdmin.get().setStatus(EStatus.DELETED);
        save(optionalAdmin.get());
        authDeleteProducer.convertAndSend(DeleteAuthModel.builder()
                .authid(optionalAdmin.get().getAuthId())
                .eStatus(optionalAdmin.get().getStatus())
                .build());
        return "Named " + optionalAdmin.get().getName() + " has been deleted";
    }


    public GetInformationResponseDto getInformation() {
        ResponseEntity<List<ManagerResponseDto>> listManager=managerServiceManager.findAll();
        ResponseEntity<List<PersonelResponseDto>> listPersonel=personelManager.findAll();

        GetInformationResponseDto dto= GetInformationResponseDto.builder()
                .managerSize(listManager.getBody().size())
                .personalSize(listPersonel.getBody().size())
                .build();
        return dto;


    }
}
