package org.group3.service;

import org.group3.dto.request.SaveRequestDto;
import org.group3.dto.request.UpdateRequestDto;
import org.group3.dto.response.CompanyFindByNameResponseDto;
import org.group3.dto.response.GetInformationForVisitorResponseDto;
import org.group3.dto.response.VisitorFindAllResponseDto;
import org.group3.dto.response.FindByIdResponseDto;
import org.group3.entity.Visitor;
import org.group3.entity.enums.EStatus;
import org.group3.exception.ErrorType;
import org.group3.exception.VisitorManagerException;
import org.group3.manager.ICompanyManager;
import org.group3.mapper.IVisitorMapper;
import org.group3.rabbitMq.model.DeleteAuthModel;
import org.group3.rabbitMq.model.UpdateAuthModel;
import org.group3.rabbitMq.producer.AuthDeleteProducer;
import org.group3.rabbitMq.producer.AuthUpdateProduce;
import org.group3.repository.VisitorRepository;
import org.group3.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VisitorService extends ServiceManager<Visitor, Long> {

    private final VisitorRepository repository;
    private final AuthUpdateProduce authUpdateProduce;
    private final AuthDeleteProducer authDeleteProducer;
    private final ICompanyManager companyManager;
    public VisitorService(VisitorRepository repository, AuthUpdateProduce authUpdateProduce, AuthDeleteProducer authDeleteProducer, ICompanyManager companyManager) {
        super(repository);
        this.repository = repository;

        this.authUpdateProduce = authUpdateProduce;
        this.authDeleteProducer = authDeleteProducer;
        this.companyManager = companyManager;
    }

    public FindByIdResponseDto findByIdDto(Long id) {
        Optional<Visitor> optionalVisitor = findById(id);
        if (optionalVisitor.isEmpty()) {
            throw new VisitorManagerException(ErrorType.ID_NOT_FOUND);
        }
        return IVisitorMapper.INSTANCE.visitorToFindByIdResponseDto(optionalVisitor.get());
    }

    public List<VisitorFindAllResponseDto> findAllDto() {
        return findAll().stream().map(IVisitorMapper.INSTANCE::visitorToFindAllResponseDto).collect(Collectors.toList());
    }

    public FindByIdResponseDto softUpdate(UpdateRequestDto dto) {

        Optional<Visitor> optionalVisitor = findById(dto.getId());
        Visitor visitor = optionalVisitor.orElseThrow(() -> new VisitorManagerException(ErrorType.USER_NOT_FOUND));
        if (optionalVisitor.get().getStatus().equals(EStatus.DELETED)) {
            throw new VisitorManagerException(ErrorType.USER_ALREADY_DELETED);
        }
        if (repository.existsByEmail(dto.getEmail()) || repository.existsByPhone(dto.getPhone())) {
            throw new VisitorManagerException(ErrorType.EMAIL_OR_PHONE_EXITS);
        }
        if (dto.getName() != null) {
            visitor.setName(dto.getName());
        }
        if (dto.getSurname() != null) {
            visitor.setSurname(dto.getSurname());
        }
        if (dto.getPhoto() != null) {
            visitor.setPhoto(dto.getPhoto());
        }
        if (dto.getGender() != null) {
            visitor.setGender(dto.getGender());
        }
        if (dto.getEmail() != null) {
            visitor.setEmail(dto.getEmail());
        }
        if (dto.getPhone() != null) {
            visitor.setPhone(dto.getPhone());
        }
        visitor.setUpdatedDate(LocalDateTime.now().toString());


        update(visitor);
        if (dto.getEmail() != null || dto.getPhone() != null) {
            authUpdateProduce.convertAndSend(UpdateAuthModel.builder()
                    .authId(visitor.getAuthId())
                    .email(visitor.getEmail())
                            .phone(visitor.getPhone())
                    .build());
        }



        return IVisitorMapper.INSTANCE.visitorToFindByIdResponseDto(visitor);
    }

    public Boolean softDelete(Long id) {
        Optional<Visitor> optionalVisitor = findById(id);
        if (optionalVisitor.isEmpty()) {
            throw new VisitorManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (optionalVisitor.get().getStatus().equals(EStatus.DELETED)) {
            throw new VisitorManagerException(ErrorType.USER_ALREADY_DELETED);
        }
        optionalVisitor.get().setStatus(EStatus.DELETED);
        save(optionalVisitor.get());
        authDeleteProducer.convertAndSend(DeleteAuthModel.builder()
                .authid(optionalVisitor.get().getAuthId())
                .eStatus(optionalVisitor.get().getStatus())
                .build());
        return true;
    }

    public CompanyFindByNameResponseDto findByCompanyName(String companyName) {
        return companyManager.findByName(companyName).getBody();
    }

    public FindByIdResponseDto findByAuthId(Long authId) {
        Optional<Visitor> optionalVisitor= repository.findByAuthId(authId);
        if (optionalVisitor.isPresent()) {
            return FindByIdResponseDto.builder()
                    .id(optionalVisitor.get().getId())
                    .name(optionalVisitor.get().getName())
                    .surname(optionalVisitor.get().getSurname())
                    .email(optionalVisitor.get().getEmail())
                    .phone(optionalVisitor.get().getPhone())
                    .photo(optionalVisitor.get().getPhoto())
                    .gender(optionalVisitor.get().getGender())
                    .createdDate(optionalVisitor.get().getCreatedDate())
                    .updatedDate(optionalVisitor.get().getUpdatedDate())
                    .build();
        }else {
            throw new VisitorManagerException(ErrorType.USER_NOT_FOUND);
        }

    }

    public List<GetInformationForVisitorResponseDto> getInformation() {
        return companyManager.getInformationForVisitor().getBody();

    }
}
