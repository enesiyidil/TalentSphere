package org.group3.service;

import org.group3.dto.request.HolidayRequestDto;
import org.group3.dto.request.HolidaySaveByPersonalRequestDto;
import org.group3.dto.response.HolidayResponseDto;
import org.group3.dto.response.HolidayFindAllByCompanyIdAndStatusPendingResponseDto;
import org.group3.entity.Holiday;
import org.group3.entity.enums.EStatus;
import org.group3.exception.ErrorType;
import org.group3.exception.HolidayServiceException;
import org.group3.manager.IPersonalManager;
import org.group3.mapper.HolidayMapper;
import org.group3.rabbit.model.HolidayModel;
import org.group3.rabbit.producer.CompanyProducer;
import org.group3.rabbit.producer.PersonalProducer;
import org.group3.repository.HolidayRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HolidayService {

    private final HolidayRepository repository;
    private final PersonalProducer personalProducer;
    private final CompanyProducer companyProducer;
    private final IPersonalManager personalManager;

    public HolidayService(HolidayRepository repository, PersonalProducer personalProducer, CompanyProducer companyProducer, IPersonalManager personalManager) {
        this.repository = repository;
        this.personalProducer = personalProducer;
        this.companyProducer = companyProducer;
        this.personalManager = personalManager;
    }

    public HolidayResponseDto save(HolidayRequestDto dto) {
        Holiday holiday = HolidayMapper.INSTANCE.saveRequestDtoToHoliday(dto);
        System.out.println(holiday);
        if (dto.getRole().equals("MANAGER")){
            holiday.setStatus(EStatus.ACTIVE);
        }
        repository.save(holiday);
        if (dto.getRole().equals("MANAGER")){
            companyProducer.addHoliday(HolidayModel.builder()
                            .holidayId(holiday.getId())
                            .companyId(holiday.getCompanyId())
                    .build());
        }else if (dto.getRole().equals("HolidayModelPERSONAL")){
            personalProducer.addHoliday(HolidayModel.builder()
                    .holidayId(holiday.getId())
                    .personalId(holiday.getPersonals().get(0))
                    .build());
        }
        return HolidayMapper.INSTANCE.holidayToResponseDto(holiday);
    }

    public Boolean saveByPersonal(HolidaySaveByPersonalRequestDto dto) {
        Holiday holiday = HolidayMapper.INSTANCE.holidaySaveByPersonalRequestDtoToHoliday(dto);
        List<Long> personals = new ArrayList<>();
        personals.add(dto.getPersonalId());
        holiday.setPersonals(personals);
        repository.save(holiday);
        return true;

    }

    public HolidayResponseDto findById(Long id) {
        Optional<Holiday> optionalHoliday = repository.findById(id);
        if (optionalHoliday.isPresent()) {
            if (optionalHoliday.get().getStatus() == EStatus.DELETED)
                throw new HolidayServiceException(ErrorType.HOLIDAY_NOT_ACTIVE);
            return HolidayMapper.INSTANCE.holidayToResponseDto(optionalHoliday.get());
        }
        throw new HolidayServiceException(ErrorType.HOLIDAY_NOT_FOUND);
    }

    public List<HolidayResponseDto> findAllByCompanyId(Long companyId) {
        return repository.findAllByCompanyId(companyId).stream()
                .filter(holiday -> holiday.getStatus() != EStatus.DELETED)
                .filter(holiday -> holiday.getStatus() == EStatus.ACTIVE)
                .map(HolidayMapper.INSTANCE::holidayToResponseDto)
                .collect(Collectors.toList());
    }

    public List<HolidayResponseDto> findAllByPersonalId(Long companyId, Long personalId, EStatus status) {
        return repository.findAllByCompanyId(companyId).stream()
                .filter(holiday -> holiday.getStatus() != EStatus.DELETED)
                .filter(holiday -> status == null || holiday.getStatus() == status)
                .filter(holiday -> holiday.getPersonals().contains(personalId))
                .map(HolidayMapper.INSTANCE::holidayToResponseDto)
                .collect(Collectors.toList());
    }

    public Boolean deleteById(Long id) {
        Optional<Holiday> optionalHoliday = repository.findById(id);
        if (optionalHoliday.isPresent()) {
            if (optionalHoliday.get().getStatus() == EStatus.DELETED)
                throw new HolidayServiceException(ErrorType.HOLIDAY_NOT_ACTIVE);
            optionalHoliday.get().setStatus(EStatus.DELETED);
            repository.save(optionalHoliday.get());
            return true;
        }
        throw new HolidayServiceException(ErrorType.HOLIDAY_NOT_FOUND);
    }
    public HolidayResponseDto setStatus(Long id, EStatus status){
        Optional<Holiday> optionalExistingHoliday = repository.findById(id);
        if ((optionalExistingHoliday.isPresent())) {
            if (optionalExistingHoliday.get().getStatus() == EStatus.DELETED)
                throw new HolidayServiceException(ErrorType.HOLIDAY_NOT_ACTIVE);
            Holiday existingHoliday = optionalExistingHoliday.get();
            existingHoliday.setApprovalDate(LocalDateTime.now().toString());
            existingHoliday.setStatus(status);
            return HolidayMapper.INSTANCE.holidayToResponseDto(repository.save(existingHoliday));
        }
        throw new HolidayServiceException(ErrorType.HOLIDAY_NOT_FOUND);
    }

    public List<HolidayResponseDto> findAllDto() {
        return repository.findAll().stream().map(HolidayMapper.INSTANCE::holidayToResponseDto).collect(Collectors.toList());
    }
    public List<HolidayFindAllByCompanyIdAndStatusPendingResponseDto> findAllByCompanyIdAndStatusPending(Long companyId) {
        List<Holiday> holidayList = repository.findAllByCompanyIdAndStatus(companyId, EStatus.PENDING);
        return holidayList.stream().map(holiday -> {
            HolidayFindAllByCompanyIdAndStatusPendingResponseDto dto =HolidayMapper.INSTANCE.holidayToHolidayFindAllByCompanyIdAndStatusPendingResponseDto(holiday);
            dto.setPersonalName(personalManager.findNameByPersonalId(holiday.getPersonals().get(0)).getBody());
            return dto;
                })
                .collect(Collectors.toList());

    }
    public Boolean acceptOrRejectHolidayById(Long id, String confirm) {
        Optional<Holiday> optionalHoliday = repository.findById(id);
        if (optionalHoliday.isPresent()){
            if ((optionalHoliday.get().getStatus().equals(EStatus.PENDING))){
                optionalHoliday.get().setStatus(confirm.equals("accept") ? EStatus.ACTIVE : EStatus.DELETED);
                optionalHoliday.get().setApprovalDate(LocalDateTime.now().toString());
                repository.save(optionalHoliday.get());
                return true;
            }
            throw new HolidayServiceException(ErrorType.HOLIDAY_NOT_PENDING);
        }
        throw new HolidayServiceException(ErrorType.HOLIDAY_NOT_FOUND);
    }

}
