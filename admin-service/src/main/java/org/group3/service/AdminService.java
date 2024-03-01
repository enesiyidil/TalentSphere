package org.group3.service;

import org.group3.dto.request.SaveRequestDto;
import org.group3.dto.request.UpdateRequestDto;
import org.group3.dto.response.*;
import org.group3.entity.Admin;
import org.group3.entity.enums.EStatus;
import org.group3.exception.AdminManagerException;
import org.group3.exception.ErrorType;
import org.group3.manager.*;
import org.group3.mapper.IAdminMapper;
import org.group3.rabbitMq.model.DeleteAuthModel;
import org.group3.rabbitMq.model.UpdateAuthModel;
import org.group3.rabbitMq.producer.AuthDeleteProducer;
import org.group3.rabbitMq.producer.AuthUpdateProduce;
import org.group3.repository.AdminRepository;
import org.group3.utility.ServiceManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService extends ServiceManager<Admin, Long> {

    private final AdminRepository repository;
    private final AuthUpdateProduce authUpdateProduce;
    private final AuthDeleteProducer authDeleteProducer;
    private final IManagerServiceManager managerServiceManager;
    private final IPersonalManager personalManager;
    private final IVisitorManager visitorManager;
    private final IPaymentManager paymentManager;
    private final ICommentManager commentManager;
    private final ICompanyManager companyManager;
    private final IAuthManager authManager;

    public AdminService(AdminRepository repository,
                        AuthUpdateProduce authUpdateProduce,
                        AuthDeleteProducer authDeleteProducer,
                        IManagerServiceManager managerServiceManager,
                        IPersonalManager personalManager,
                        IVisitorManager visitorManager,
                        IPaymentManager paymentManager,
                        ICommentManager commentManager,
                        ICompanyManager companyManager,
                        IAuthManager authManager) {
        super(repository);
        this.repository = repository;
        this.authUpdateProduce = authUpdateProduce;
        this.authDeleteProducer = authDeleteProducer;
        this.managerServiceManager = managerServiceManager;
        this.personalManager = personalManager;
        this.visitorManager = visitorManager;
        this.paymentManager = paymentManager;
        this.commentManager = commentManager;
        this.companyManager = companyManager;
        this.authManager = authManager;
    }

    @Transactional
    public Boolean saveDto(SaveRequestDto dto) {
        if (repository.existsByEmail(dto.getEmail()) || repository.existsByPhone(dto.getPhone())) {
            throw new AdminManagerException(ErrorType.EMAIL_OR_PHONE_EXITS);
        }
        Admin admin = IAdminMapper.INSTANCE.saveRequestDtoToAdmin(dto);
        admin.setAuthId(authManager.adminSave(IAdminMapper.INSTANCE.saveRequestDtoToAdminSaveRequestDto(dto)).getBody());
        save(admin);
        return true;
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

    @Transactional
    public UpdateResponseDto softUpdate(UpdateRequestDto dto) {
        Optional<Admin> optionalAdmin = findById(dto.getId());
        Admin admin = optionalAdmin.orElseThrow(() -> new AdminManagerException(ErrorType.USER_NOT_FOUND));
        if (optionalAdmin.get().getStatus().equals(EStatus.DELETED)) {
            throw new AdminManagerException(ErrorType.USER_ALREADY_DELETED);
        }
        if (repository.existsByEmail(dto.getEmail()) || repository.existsByPhone(dto.getPhone())) {
            throw new AdminManagerException(ErrorType.EMAIL_OR_PHONE_EXITS);
        }
        if (dto.getName() != null) {
            admin.setPhone(dto.getName());
        }
        if (dto.getSurname() != null) {
            admin.setPhone(dto.getSurname());
        }

        if (dto.getEmail() != null) {
            admin.setEmail(dto.getEmail());
        }
        if (dto.getPhone() != null) {
            admin.setPhone(dto.getPhone());
        }
        if (dto.getPhoto() != null) {
            admin.setPhoto(dto.getPhoto());
        }

        admin.setUpdatedDate(System.currentTimeMillis());

        update(admin);
        authUpdateProduce.convertAndSend(UpdateAuthModel.builder()
                .authid(admin.getAuthId())
                .email(admin.getEmail())
                .phone(admin.getPhone())
                .build());

        return UpdateResponseDto.builder()
                .id(admin.getId())
                .name(admin.getName())
                .surname(admin.getSurname())
                .email(admin.getEmail())
                .phone(admin.getPhone())
                .photo(admin.getPhoto())
                .createdDate(admin.getCreatedDate())
                .updatedDate(admin.getUpdatedDate())
                .build();
    }

    @Transactional
    public Boolean softDelete(Long id) {
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
        return true;
    }

    public GetInformationResponseDto getInformation() {
        ResponseEntity<List<ManagerResponseDto>> listManager=managerServiceManager.findAll();
        ResponseEntity<List<PersonalResponseDto>> listPersonal= personalManager.findAll();
        ResponseEntity<List<VisitorFindAllResponseDto>> listVisitor=visitorManager.findAll();
        ResponseEntity<List<PaymentFindAllInfoResponseDto>> listPayment=paymentManager.findAllInfo();
        ResponseEntity<List<CommentFindAllResponseDto>> listComment=commentManager.findAll();
        ResponseEntity<List<CompanyFindAllInfoResponseDto>> listCompany=companyManager.findAllInfo();

        return GetInformationResponseDto.builder()
                .managerSize(Objects.requireNonNull(listManager.getBody()).size())
                .personalSize(Objects.requireNonNull(listPersonal.getBody()).size())
                .visitorSize(Objects.requireNonNull(listVisitor.getBody()).size())
                .paymentSize(Objects.requireNonNull(listPayment.getBody()).size())
                .commentSize(Objects.requireNonNull(listComment.getBody()).size())
                .companySize(Objects.requireNonNull(listCompany.getBody()).size())
                .build();


    }
}
