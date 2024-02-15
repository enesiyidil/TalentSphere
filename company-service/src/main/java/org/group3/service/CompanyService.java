package org.group3.service;

import org.group3.dto.request.CompanySaveRequestDto;
import org.group3.dto.request.CompanyUpdateRequestDto;
import org.group3.entity.Company;
import org.group3.entity.enums.EStatus;
import org.group3.exception.CompanyServiceException;
import org.group3.exception.ErrorType;
import org.group3.mapper.CompanyMapper;
import org.group3.rabbit.model.CompanyModel;
import org.group3.rabbit.model.HolidayModel;
import org.group3.rabbit.model.PaymentModel;
import org.group3.rabbit.producer.ManagerProducer;
import org.group3.repository.CompanyRepository;
import org.group3.utility.ServiceUtility;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private final CompanyRepository repository;

    private final ServiceUtility serviceUtility;

    private final ManagerProducer managerProducer;

    private final MessageSource messageSource;

    public CompanyService(CompanyRepository repository, ServiceUtility serviceUtility, ManagerProducer managerProducer, MessageSource messageSource) {
        this.repository = repository;
        this.serviceUtility = serviceUtility;
        this.managerProducer = managerProducer;
        this.messageSource = messageSource;
//        this.greet();
    }

//    public void greet() {
//        String greeting = messageSource.getMessage("hello", null, Locale.getDefault());
//        System.out.println(greeting); // Hello (eğer varsayılan dil İngilizce ise)
//    }

    public Company save(CompanySaveRequestDto dto) {
        Company company = repository.save(CompanyMapper.INSTANCE.saveRequestDtoToCompany(dto));
        managerProducer.addCompany(CompanyModel.builder()
                        .companyId(company.getId())
                        .managerId(company.getManagerId())
                .build());
        return company;
    }

    public Company findById(Long id) {
        Optional<Company> optionalCompany = repository.findById(id);
        if (optionalCompany.isPresent()) {
            if (optionalCompany.get().getStatus() == EStatus.DELETED)
                throw new CompanyServiceException(ErrorType.COMPANY_NOT_ACTIVE);
            return optionalCompany.get();
        }
        throw new CompanyServiceException(ErrorType.COMPANY_NOT_FOUND);
    }

    public List<Company> findAllByManagerId(Long managerId) {
        return repository.findAllByManagerId(managerId).stream().filter(company -> company.getStatus() == EStatus.ACTIVE).collect(Collectors.toList());
    }

    public Boolean deleteById(Long id) {
        return serviceUtility.softDelete(id, repository);
    }

    public Company update(CompanyUpdateRequestDto dto) {
        Company existingCompany = this.findById(dto.getId());
        if (dto.getManagerId() != null) {
            existingCompany.setManagerId(dto.getManagerId());
        }
        if (dto.getName() != null) {
            existingCompany.setName(dto.getName());
        }
        if (dto.getGallery() != null) {
            existingCompany.setGallery(dto.getGallery());
        }
        existingCompany.setUpdatedDateTime(LocalDateTime.now());
        return repository.save(existingCompany);
    }

    public void addCommunication(Long id, Long phoneId) {
        Company existingCompany = this.findById(id);
        if (existingCompany.getCommunications().contains(phoneId))
            throw new CompanyServiceException(ErrorType.COMMUNICATION_ALREADY_EXISTS);
        existingCompany.getCommunications().add(phoneId);
        repository.save(existingCompany);
    }

    public void addPayment(PaymentModel model) {
        Company existingCompany = this.findById(model.getCompanyId());
        if (existingCompany.getPayments().contains(model.getPaymentId()))
            throw new CompanyServiceException(ErrorType.PAYMENT_ALREADY_EXISTS);
        existingCompany.getPayments().add(model.getPaymentId());
        repository.save(existingCompany);
    }

    public void addPersonal(Long id, Long personalId) {
        Company existingCompany = this.findById(id);
        if (existingCompany.getPersonals().contains(personalId))
            throw new CompanyServiceException(ErrorType.PERSONAL_ALREADY_EXISTS);
        existingCompany.getPersonals().add(personalId);
        repository.save(existingCompany);
    }

    public void addShift(Long id, Long shiftId) {
        Company existingCompany = this.findById(id);
        if (existingCompany.getShifts().contains(shiftId))
            throw new CompanyServiceException(ErrorType.SHIFT_ALREADY_EXISTS);
        existingCompany.getShifts().add(shiftId);
        repository.save(existingCompany);
    }

    public void addHoliday(HolidayModel model) {
        Company existingCompany = this.findById(model.getCompanyId());
        if (existingCompany.getHolidays().contains(model.getHolidayId()))
            throw new CompanyServiceException(ErrorType.HOLIDAY_ALREADY_EXISTS);
        existingCompany.getShifts().add(model.getHolidayId());
        repository.save(existingCompany);
    }

    public List<Company> findAll() {
        return repository.findAll();
    }


    public List<Company> findByPersonalId(Long personalId) {
        return repository.findByPersonalsContains(personalId);
    }
}
