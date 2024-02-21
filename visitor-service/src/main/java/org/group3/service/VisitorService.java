package org.group3.service;

import org.group3.dto.request.SaveRequestDto;
import org.group3.dto.request.UpdateRequestDto;
import org.group3.dto.response.CompanyFindByNameResponseDto;
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

    public String saveDto(SaveRequestDto dto) {
        if (repository.existsByEmail(dto.getEmail()) || repository.existsByPhone(dto.getPhone())) {
            throw new VisitorManagerException(ErrorType.EMAIL_OR_PHONE_EXITS);
        }
        Visitor visitor = IVisitorMapper.INSTANCE.saveRequestDtoToVisitor(dto);
        save(visitor);
        return "kayıt işlemi başarılı";
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

        if (dto.getEmail() != null) {
            visitor.setEmail(dto.getEmail());
        }
        if (dto.getPhone() != null) {
            visitor.setPhone(dto.getPhone());
        }
        visitor.setUpdatedDate(System.currentTimeMillis());

        update(visitor);
        authUpdateProduce.convertAndSend(UpdateAuthModel.builder()
                .authid(visitor.getAuthId())
                .email(visitor.getEmail())
                .build());

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

    public List<FindByIdResponseDto> findByAuthId(Long authId) {
        List<Visitor> visitorList= repository.findByAuthId(authId);
        return visitorList.stream()
                .map(IVisitorMapper.INSTANCE::visitorToFindByIdResponseDto)
                .collect(Collectors.toList());
    }
}
