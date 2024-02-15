package org.group3.service;

import org.group3.dto.request.LoginRequestDto;
import org.group3.dto.request.RegisterRequestDto;
import org.group3.dto.request.UpdatePasswordRequestDto;
import org.group3.dto.response.FindAllResponseDto;
import org.group3.dto.response.FindByIdRespoonseDto;
import org.group3.dto.response.LoginResponseDto;
import org.group3.dto.response.RegisterResponseDto;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService extends ServiceManager<Auth, Long> {

    private final AuthRepository repository;

    private final JwtTokenManager tokenManager;
    private final AdminSave adminSave;

    private final VisitorSaveProduce visitorSaveProduce;

    private final ManagerSaveProducer managerSaveProducer;

    private final MailSenderProduce mailSenderProduce;

    private final CodeGenerator codeGenerator;
    private final SmsSenderProduce smsSenderProduce;

    public AuthService(AuthRepository repository, JwtTokenManager tokenManager, AdminSave adminSave, VisitorSaveProduce visitorSaveProduce, ManagerSaveProducer managerSaveProducer, MailSenderProduce mailSenderProduce, CodeGenerator codeGenerator, SmsSenderProduce smsSenderProduce) {
        super(repository);
        this.repository = repository;
        this.tokenManager = tokenManager;
        this.adminSave = adminSave;
        this.visitorSaveProduce = visitorSaveProduce;
        this.managerSaveProducer = managerSaveProducer;
        this.mailSenderProduce = mailSenderProduce;
        this.codeGenerator = codeGenerator;
        this.smsSenderProduce = smsSenderProduce;
    }

    public RegisterResponseDto register(RegisterRequestDto dto) {
        //personelse hata fırlat
        if (repository.existsByEmail(dto.getEmail())) {
            throw new AuthManagerException(ErrorType.REGISTER_EMAIL_ALREADY_EXISTS);
        }
        Auth auth = IAuthMapper.INSTANCE.registerRequestDtotoAuth(dto);
        //auth.setCreatedDate(System.currentTimeMillis());
        save(auth);


        if (auth.getRole().equals(ERole.ADMIN)) {
            adminSave.convertAndSend(SaveAuthModel.builder()
                    .authId(auth.getId())
                    .email(auth.getEmail())
                    .name(dto.getName())
                    .surname(dto.getSurname())
                    .phone(dto.getPhone())
                    .build());
            auth.setStatus(EStatus.ACTIVE);
            update(auth);
        }
        if (auth.getRole().equals(ERole.VISITOR)) {
            visitorSaveProduce.convertAndSend(SaveAuthModel.builder()
                    .authId(auth.getId())
                    .email(auth.getEmail())
                    .name(dto.getName())
                    .surname(dto.getSurname())
                    .phone(dto.getPhone())
                    .build());
        }
        if (auth.getRole().equals(ERole.MANAGER)) {
            managerSaveProducer.convertAndSend(SaveAuthModel.builder()
                    .authId(auth.getId())
                    .email(auth.getEmail())
                    .name(dto.getName())
                    .surname(dto.getSurname())
                    .phone(dto.getPhone())
                    .title(dto.getTitle())
                    .build());
        }


        String url = "http://localhost:9092/auth/activate?t=" + tokenManager.createToken(auth.getId(), dto.getRole()).get();
        mailSenderProduce.convertAndSend(SendMailModel.builder()
                .email(auth.getEmail())
                .subject("Aktivasyon")
                .content(url)
                .build());
        System.out.println(url);

        return IAuthMapper.INSTANCE.authToRegisterResponseDto(auth);
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
            String token = tokenManager.createToken(optionalAuth.get().getId(), optionalAuth.get().getRole(), code).orElseThrow(() -> new AuthManagerException(ErrorType.TOKEN_NOT_CREATED));
            return LoginResponseDto.builder()
                    .token(token)
                    .authId(optionalAuth.get().getId())
                    .role(optionalAuth.get().getRole())
                    .build();
        }
        String token = tokenManager.createToken(optionalAuth.get().getId(), optionalAuth.get().getRole()).orElseThrow(() -> new AuthManagerException(ErrorType.TOKEN_NOT_CREATED));
        return LoginResponseDto.builder()
                .token(token)
                .authId(optionalAuth.get().getId())
                .role(optionalAuth.get().getRole())
                .build();
    }

    public List<FindAllResponseDto> findAll(String token, EStatus status) {
        Optional<Long> idFromToken;
        try {
            idFromToken = tokenManager.decodeToken(token);
        } catch (Exception e) {
            throw new AuthManagerException(ErrorType.INVALID_TOKEN);
        }
        return findAll().stream().filter(auth -> status == null || auth.getStatus() == status)
                .map(IAuthMapper.INSTANCE::authToFindAllResponseDto).collect(Collectors.toList());
    }

    public FindByIdRespoonseDto findByIdDto(Long id) {
        Optional<Auth> optionalAuth = findById(id);
        if (optionalAuth.isEmpty()) {
            throw new AuthManagerException(ErrorType.ID_NOT_FOUND);
        }
        return IAuthMapper.INSTANCE.authToFindByIdResponseDto(optionalAuth.get());
    }

    public String softDelete(Long id) {
        Optional<Auth> optionalAuth = findById(id);
        if (optionalAuth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (optionalAuth.get().getStatus().equals(EStatus.DELETED)) {
            throw new AuthManagerException(ErrorType.USER_ALREADY_DELETED);
        }
        optionalAuth.get().setStatus(EStatus.DELETED);
        save(optionalAuth.get());
        return "User named " + optionalAuth.get().getUsername() + " has been deleted";
    }

    public void softUpdate(UpdateAuthModel model) {
        Optional<Auth> optionalAuth = findById(model.getAuthid());
        Auth auth = optionalAuth.orElseThrow(() -> new AuthManagerException(ErrorType.USER_NOT_FOUND));
        if (optionalAuth.get().getStatus().equals(EStatus.DELETED)) {
            throw new AuthManagerException(ErrorType.USER_ALREADY_DELETED);
        }
        if (repository.existsByEmail(model.getEmail())) {
            throw new AuthManagerException(ErrorType.EMAIL_EXITS);
        }

        auth.setEmail(model.getEmail());
        auth.setUpdatedDate(System.currentTimeMillis());
        update(auth);

    }


    public Long personalSave(RegisterRequestDto dto) {
        Auth auth = IAuthMapper.INSTANCE.registerRequestDtotoAuth(dto);
        save(auth);
        return auth.getId();
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
                return "Hesap zaten aktif";
            }
            case PENDING -> {
                auth.setStatus(EStatus.ACTIVE);
                update(auth);

                //activeStatusProducer.convertAndSendToRabbit(auth.getId());
                return "Aktivasyon başarılı";
            }
            case BANNED -> {
                return "Hesabınız banlı";
            }
            case DELETED -> {
                return "Hesabınız silinmiş";

            }
            default -> {
                throw new AuthManagerException(ErrorType.INTERNAL_ERROR_SERVER);
            }
        }
    }

    public String updatePassword(UpdatePasswordRequestDto dto) {

        Optional<Auth> optionalAdmin = findById(dto.getId());
        Auth auth = optionalAdmin.orElseThrow(() -> new AuthManagerException(ErrorType.USER_NOT_FOUND));
        if (optionalAdmin.get().getStatus().equals(EStatus.DELETED)) {
            throw new AuthManagerException(ErrorType.USER_ALREADY_DELETED);
        }
        auth.setPassword(dto.getPassword());
        update(auth);
        return "Başarılı";

    }
}
