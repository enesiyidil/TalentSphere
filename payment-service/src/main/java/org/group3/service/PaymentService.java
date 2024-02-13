package org.group3.service;

import org.group3.dto.request.PaymentRequestDto;
import org.group3.entity.Payment;
import org.group3.entity.enums.EStatus;
import org.group3.exception.ErrorType;
import org.group3.exception.PaymentServiceException;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    private final ElasticsearchTemplate elasticsearchTemplate;

    private final CompanyProducer companyProducer;

    public PaymentService(PaymentRepository repository, ElasticsearchTemplate elasticsearchTemplate, CompanyProducer companyProducer) {
        this.repository = repository;
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.companyProducer = companyProducer;
    }

    public Payment save(PaymentRequestDto dto) {
        Payment payment = repository.save(PaymentMapper.INSTANCE.requestDtoToManager(dto));
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

    public Payment update(PaymentRequestDto dto) {
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
            existingPayment.setUpdatedDate(System.currentTimeMillis());
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
            optionalExistingPayment.get().setPaymentDate(System.currentTimeMillis());
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
}
