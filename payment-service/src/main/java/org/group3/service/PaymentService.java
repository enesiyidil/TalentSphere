package org.group3.service;

import org.group3.dto.request.AcceptOrRejectPaymentByIdRequestDto;
import org.group3.dto.request.PaymentRequestDto;
import org.group3.dto.request.PaymentUpdateRequestDto;
import org.group3.dto.response.PaymentFindAllByNotApproveResponse;
import org.group3.dto.response.PaymentFindAllInfoResponseDto;
import org.group3.dto.response.PaymentInformationForVisitorResponseDto;
import org.group3.entity.Payment;
import org.group3.entity.enums.ERole;
import org.group3.entity.enums.EStatus;
import org.group3.entity.enums.EType;
import org.group3.exception.ErrorType;
import org.group3.exception.PaymentServiceException;
import org.group3.manager.IManagerServiceManager;
import org.group3.manager.IPersonalManager;
import org.group3.mapper.PaymentMapper;
import org.group3.rabbit.model.PaymentModel;
import org.group3.rabbit.producer.CompanyProducer;
import org.group3.repository.PaymentRepository;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    private final ElasticsearchTemplate elasticsearchTemplate;

    private final CompanyProducer companyProducer;

    private final IManagerServiceManager managerServiceManager;

    private final IPersonalManager personalManager;

    public PaymentService(PaymentRepository repository, ElasticsearchTemplate elasticsearchTemplate, CompanyProducer companyProducer, IManagerServiceManager managerServiceManager, IPersonalManager personalManager) {
        this.repository = repository;
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.companyProducer = companyProducer;
        this.managerServiceManager = managerServiceManager;
        this.personalManager = personalManager;
    }

    public Payment save(PaymentRequestDto dto) {
        Payment payment = PaymentMapper.INSTANCE.requestDtoToPayment(dto);
        payment.setId(UUID.randomUUID().toString());
        if (dto.getRole().equals(ERole.MANAGER)){
            payment.setStatus(EStatus.ACCEPT);
        }
        payment.setUpdatedDate(LocalDateTime.now().toString());
        repository.save(payment);
        companyProducer.addPayment(PaymentModel.builder()
                .paymentId(payment.getId())
                .companyId(payment.getCompanyId())
                .build());
        return payment;
    }

    public Payment findById(String id) {
        Optional<Payment> optionalPayment = repository.findById(id);
        if (optionalPayment.isPresent()) {
            if (optionalPayment.get().getStatus() == EStatus.DELETED)
                throw new PaymentServiceException(ErrorType.PAYMENT_NOT_ACTIVE);
            return optionalPayment.get();
        }
        throw new PaymentServiceException(ErrorType.PAYMENT_NOT_FOUND);
    }

    public Iterable<Payment> findAll() {
        return repository.findAll();
    }

    public List<PaymentFindAllInfoResponseDto> findAllInfo() {
        Iterable<Payment> payments=repository.findAll();
        List<Payment> paymentList=new ArrayList<>();
        payments.forEach(paymentList::add);
        return paymentList.stream().map(PaymentMapper.INSTANCE::paymentToPaymentFindAllInfoResponseDto)
                .collect(Collectors.toList());

    }

    public Boolean deleteById(String id) {
        Optional<Payment> optionalPayment = repository.findById(id);
        if (optionalPayment.isPresent()) {
            if (optionalPayment.get().getStatus() == EStatus.DELETED)
                throw new PaymentServiceException(ErrorType.PAYMENT_NOT_ACTIVE);
            optionalPayment.get().setStatus(EStatus.DELETED);
            repository.save(optionalPayment.get());
            return true;
        }
        throw new PaymentServiceException(ErrorType.PAYMENT_NOT_FOUND);
    }

    public Payment update(PaymentUpdateRequestDto dto) {
        Optional<Payment> optionalExistingPayment = repository.findById(dto.getId());
        if ((optionalExistingPayment.isPresent())) {
            if (optionalExistingPayment.get().getStatus() == EStatus.DELETED)
                throw new PaymentServiceException(ErrorType.PAYMENT_NOT_ACTIVE);
            Payment existingPayment = optionalExistingPayment.get();
            if (dto.getAmount() != null) {
                existingPayment.setAmount(dto.getAmount());
            }
            if (dto.getDescription() != null) {
                existingPayment.setDescription(dto.getDescription());
            }
            if (dto.getDueDate() != null) {
                existingPayment.setDueDate(dto.getDueDate());
            }
            if (dto.getType() != null) {
                existingPayment.setType(dto.getType());
            }
            existingPayment.setUpdatedDate(LocalDateTime.now().toString());
            return repository.save(existingPayment);
        }
        throw new PaymentServiceException(ErrorType.PAYMENT_NOT_FOUND);
    }

    public Payment completePayment(String id) {
        Optional<Payment> optionalExistingPayment = repository.findById(id);
        if ((optionalExistingPayment.isPresent())) {
            if (optionalExistingPayment.get().getStatus() == EStatus.DELETED)
                throw new PaymentServiceException(ErrorType.PAYMENT_NOT_ACTIVE);
            if (optionalExistingPayment.get().getPaymentDate() != null)
                throw new PaymentServiceException(ErrorType.PAYMENT_ALREADY_MADE);
            optionalExistingPayment.get().setPaymentDate(LocalDateTime.now().toString());
            return repository.save(optionalExistingPayment.get());
        }
        throw new PaymentServiceException(ErrorType.PAYMENT_NOT_FOUND);
    }

    public List<Payment> getPaymentsByDueDateRange(Long companyId, Long startTime, Long endTime) {
        Criteria criteria = new Criteria("dueDate").between(startTime, endTime);
        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
        SearchHits<Payment> searchHits = elasticsearchTemplate.search(criteriaQuery, Payment.class);
        return searchHits.stream().map(SearchHit::getContent).filter(payment -> Objects.equals(payment.getCompanyId(), companyId)).collect(Collectors.toList());
    }

    public List<Payment> getPaymentsByPaymentRange(Long companyId, Long startTime, Long endTime) {
        Criteria criteria = new Criteria("paymentDate").between(startTime, endTime);
        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
        SearchHits<Payment> searchHits = elasticsearchTemplate.search(criteriaQuery, Payment.class);
        return searchHits.stream().map(SearchHit::getContent).filter(payment -> Objects.equals(payment.getCompanyId(), companyId)).collect(Collectors.toList());
    }

    public List<Payment> getPaymentsByCreatedDateRange(Long companyId, Long startTime, Long endTime) {
        Criteria criteria = new Criteria("createdDate").between(startTime, endTime);
        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
        SearchHits<Payment> searchHits = elasticsearchTemplate.search(criteriaQuery, Payment.class);
        return searchHits.stream().map(SearchHit::getContent).filter(payment -> Objects.equals(payment.getCompanyId(), companyId)).collect(Collectors.toList());
    }

    public List<Payment> findAllByCompanyId(Long companyId) {
        Criteria criteria = new Criteria("companyId").matches(companyId);
        CriteriaQuery criteriaQuery = new CriteriaQuery(criteria);
        SearchHits<Payment> searchHits = elasticsearchTemplate.search(criteriaQuery, Payment.class);
        return searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }


    public PaymentInformationForVisitorResponseDto getInformationForVisitor(Long companyId) {
        List<Payment> payments = repository.findAllByCompanyId(companyId);
        return PaymentInformationForVisitorResponseDto.builder()
                .paymentNumber(payments.size())
                .turnOver(payments.stream().filter(payment -> payment.getType() == EType.INCOME )
                        .map((Payment::getAmount))
                        .reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue() -
                        payments.stream().filter(payment -> payment.getType() == EType.EXPENSE )
                                .map((Payment::getAmount))
                                .reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue())
                .build();
    }
    public List<PaymentFindAllByNotApproveResponse> findAllByNotApprove(Long companyId) {
        List<Payment> paymentList = repository.findAllByStatusEqualsAndCompanyId(EStatus.PENDING, companyId);
        return paymentList.stream().map(payment -> {
            String name = null;
            if (payment.getRole().equals(ERole.MANAGER)){
                name = managerServiceManager.findNameByAuthId(payment.getAuthId()).getBody();
            }else if (payment.getRole().equals(ERole.PERSONAL)){
                name = personalManager.findNameByPersonalAuthId(payment.getAuthId()).getBody();
            }
            return PaymentFindAllByNotApproveResponse.builder()
                        .id(payment.getId())
                        .nameOfCreator(name)
                        .amount(payment.getAmount())
                        .dueDate(payment.getDueDate())
                        .updatedDate(payment.getUpdatedDate())
                        .description(payment.getDescription())
                        .type(payment.getType())
                        .currency(payment.getCurrency())
                        .expenditureType(payment.getExpenditureType())
                        .requestDate(payment.getRequestDate())
                        .build();
        })
                .collect(Collectors.toList());
    }

    public Boolean acceptOrRejectPaymentById(AcceptOrRejectPaymentByIdRequestDto dto) {
        Optional<Payment> optionalPayment = repository.findById(dto.getId());
        if (optionalPayment.isPresent()){
            if ((optionalPayment.get().getStatus().equals(EStatus.PENDING))){
                optionalPayment.get().setStatus(Objects.equals(dto.getConfirm(), "accept") ? EStatus.ACCEPT : EStatus.REJECTED);
                repository.save(optionalPayment.get());
                return true;
            }
            throw new PaymentServiceException(ErrorType.PAYMENT_NOT_ACTIVE);
        }
        throw new PaymentServiceException(ErrorType.PAYMENT_NOT_FOUND);
    }
}
