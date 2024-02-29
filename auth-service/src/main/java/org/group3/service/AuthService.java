package org.group3.service;

import org.group3.dto.request.*;
import org.group3.dto.response.FindAllResponseDto;
import org.group3.dto.response.FindByIdResponseDto;
import org.group3.dto.response.LoginResponseDto;
import org.group3.entity.Auth;
import org.group3.entity.Enums.ERole;
import org.group3.entity.Enums.EStatus;
import org.group3.exception.AuthManagerException;
import org.group3.exception.ErrorType;
import org.group3.mapper.IAuthMapper;
import org.group3.rabbitMq.model.SaveAuthModel;
import org.group3.rabbitMq.model.SendMailModel;
import org.group3.rabbitMq.model.SmsSenderModel;
import org.group3.rabbitMq.model.UpdateAuthModel;
import org.group3.rabbitMq.producer.*;
import org.group3.repository.AuthRepository;
import org.group3.utility.CodeGenerator;
import org.group3.utility.JwtTokenManager;
import org.group3.utility.ServiceManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static org.group3.constant.EndPoints.*;
import static org.group3.constant.Messages.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService extends ServiceManager<Auth, Long> {

    @Value("${apiGatewayUrl}")
    private String apiGatewayUrl;
    private final AuthRepository repository;
    private final JwtTokenManager tokenManager;
    private final VisitorSaveProduce visitorSaveProduce;
    private final MailSenderProduce mailSenderProduce;
    private final CodeGenerator codeGenerator;
    private final SmsSenderProduce smsSenderProduce;

    public AuthService(AuthRepository repository,
                       JwtTokenManager tokenManager,
                       VisitorSaveProduce visitorSaveProduce,
                       MailSenderProduce mailSenderProduce,
                       CodeGenerator codeGenerator,
                       SmsSenderProduce smsSenderProduce) {
        super(repository);
        this.repository = repository;
        this.tokenManager = tokenManager;
        this.visitorSaveProduce = visitorSaveProduce;
        this.mailSenderProduce = mailSenderProduce;
        this.codeGenerator = codeGenerator;
        this.smsSenderProduce = smsSenderProduce;
    }

    @Transactional
    public Boolean register(RegisterRequestDto dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new AuthManagerException(ErrorType.REGISTER_EMAIL_ALREADY_EXISTS);
        }
        Auth auth = IAuthMapper.INSTANCE.registerRequestDtotoAuth(dto);
        auth.setRole(ERole.VISITOR);
        auth.setUpdatedDate(LocalDateTime.now().toString());
        save(auth);

        visitorSaveProduce.convertAndSend(SaveAuthModel.builder()
                .authId(auth.getId())
                .email(auth.getEmail())
                .name(dto.getName())
                .surname(dto.getSurname())
                .gender(dto.getGender())
                .phone(dto.getPhone())
                .photo(dto.getPhoto())
                .build());

        String token = tokenManager.createToken(auth.getId(), auth.getRole()).orElseThrow(
                () -> new AuthManagerException(ErrorType.TOKEN_NOT_CREATED)
        );

        String url = apiGatewayUrl + AUTH + ACTIVATE + "?t=" + token;
        mailSenderProduce.convertAndSend(SendMailModel.builder()
                .email(auth.getEmail())
                .subject(ACTIVATE_MESSAGE)
                .content(url)
                .build());
        return true;
    }

    @Transactional
    public Long managerOrPersonalSave(ManagerOrPersonalSaveRequestDto dto) {
        Auth auth = IAuthMapper.INSTANCE.managerOrPersonalSaveRequestDtoToAuth(dto);
        auth.setUsername(dto.getName() + dto.getSurname());
        auth.setPassword(codeGenerator.generateCode());
        auth.setStatus(EStatus.ACTIVE);
        auth.setUpdatedDate(LocalDateTime.now().toString());
        save(auth);
        mailSenderProduce.convertAndSend(SendMailModel.builder()
                .email(auth.getEmail())
                .subject(ACCOUNT_DETAILS_MESSAGE)
                .content(auth.getPassword())
                .build());
        return auth.getId();
    }
    @Transactional
    public Long adminSave(AdminSaveRequestDto dto) {
        Auth auth = IAuthMapper.INSTANCE.adminSaveRequestDtoToAuth(dto);
        auth.setStatus(EStatus.ACTIVE);
        auth.setRole(ERole.ADMIN);
        auth.setUpdatedDate(LocalDateTime.now().toString());
        save(auth);
        return auth.getId();
    }

    public LoginResponseDto login(LoginRequestDto dto) {
        Optional<Auth> optionalAuth = repository.findOptionalByEmailAndPassword(dto.getEmail(), dto.getPassword());

        if (optionalAuth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }

        if (optionalAuth.get().getStatus() != EStatus.ACTIVE) {
            throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }
        if (optionalAuth.get().getRole() == ERole.MANAGER) {
            String code = codeGenerator.generateCode();
            smsSenderProduce.convertAndSend(SmsSenderModel.builder()
                    .toNumber(optionalAuth.get().getPhone())
                    .message(code)
                    .build());
            String token = tokenManager.createToken(optionalAuth.get().getId(), optionalAuth.get().getRole(), code)
                    .orElseThrow(() -> new AuthManagerException(ErrorType.TOKEN_NOT_CREATED));
            return LoginResponseDto.builder()
                    .token(token)
                    .authId(optionalAuth.get().getId())
                    .role(optionalAuth.get().getRole())
                    .build();
        }
        String token = tokenManager.createToken(optionalAuth.get().getId(), optionalAuth.get().getRole())
                .orElseThrow(() -> new AuthManagerException(ErrorType.TOKEN_NOT_CREATED));
        return LoginResponseDto.builder()
                .token(token)
                .authId(optionalAuth.get().getId())
                .role(optionalAuth.get().getRole())
                .build();
    }

    public List<FindAllResponseDto> findAll(EStatus status) {
        return findAll().stream().filter(auth -> status == null || auth.getStatus() == status)
                .map(IAuthMapper.INSTANCE::authToFindAllResponseDto).collect(Collectors.toList());
    }

    public FindByIdResponseDto findByIdDto(Long id) {
        Optional<Auth> optionalAuth = findById(id);
        if (optionalAuth.isEmpty()) {
            throw new AuthManagerException(ErrorType.ID_NOT_FOUND);
        }
        return IAuthMapper.INSTANCE.authToFindByIdResponseDto(optionalAuth.get());
    }

    public Boolean softDelete(Long id) {
        Optional<Auth> optionalAuth = findById(id);
        if (optionalAuth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (optionalAuth.get().getStatus().equals(EStatus.DELETED)) {
            throw new AuthManagerException(ErrorType.USER_ALREADY_DELETED);
        }
        optionalAuth.get().setStatus(EStatus.DELETED);
        save(optionalAuth.get());
        return true;
    }

    public void softUpdate(UpdateAuthModel model) {
        Optional<Auth> optionalAuth = findById(model.getAuthId());
        Auth auth = optionalAuth.orElseThrow(() -> new AuthManagerException(ErrorType.USER_NOT_FOUND));
        if (optionalAuth.get().getStatus().equals(EStatus.DELETED)) {
            throw new AuthManagerException(ErrorType.USER_ALREADY_DELETED);
        }
        if (model.getEmail() != null){
            auth.setEmail(model.getEmail());
        }
        if (model.getPhone() != null){
            auth.setPhone(model.getPhone());
            System.out.println(auth.getPhone());
        }
        auth.setUpdatedDate(LocalDateTime.now().toString());
        update(auth);
    }


    public String activateCode(String t) {
        Optional<Auth> optionalAuth = findById(tokenManager.decodeToken(t).get());
        if (optionalAuth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        return statusControl(optionalAuth.get());
    }

    private String statusControl(Auth auth) {
        switch (auth.getStatus()) {
            case ACTIVE -> {
                return ACCOUNT_ALREADY_ACTIVATED_MESSAGE;
            }
            case PENDING -> {
                auth.setStatus(EStatus.ACTIVE);
                update(auth);
                return ACTIVATION_SUCCESSFUL_MESSAGE;
            }
            case BANNED -> {
                return YOUR_ACCOUNT_HAS_BEEN_BLOCKED_MESSAGE;
            }
            case DELETED -> {
                return YOUR_ACCOUNT_HAS_BEEN_DELETED_MESSAGE;
            }
            default -> {
                throw new AuthManagerException(ErrorType.INTERNAL_ERROR_SERVER);
            }
        }
    }

    public Boolean updatePassword(UpdatePasswordRequestDto dto) {
        Optional<Auth> optionalAdmin = findById(dto.getId());
        Auth auth = optionalAdmin.orElseThrow(() -> new AuthManagerException(ErrorType.USER_NOT_FOUND));
        if (optionalAdmin.get().getStatus().equals(EStatus.DELETED)) {
            throw new AuthManagerException(ErrorType.USER_ALREADY_DELETED);
        }
        auth.setPassword(dto.getPassword());
        update(auth);
        return true;
    }


}
