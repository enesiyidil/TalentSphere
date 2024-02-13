package org.group3.service;

import org.group3.dto.request.HolidayRequestDto;
import org.group3.dto.response.HolidayResponseDto;
import org.group3.entity.Holiday;
import org.group3.entity.enums.EStatus;
import org.group3.exception.ErrorType;
import org.group3.exception.HolidayServiceException;
import org.group3.mapper.HolidayMapper;
import org.group3.rabbit.model.HolidayModel;
import org.group3.rabbit.producer.CompanyProducer;
import org.group3.rabbit.producer.PersonalProducer;
import org.group3.repository.HolidayRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HolidayService {

    private final HolidayRepository repository;
    
    private final PersonalProducer personalProducer;
    
    private final CompanyProducer companyProducer;

    public HolidayService(HolidayRepository repository, PersonalProducer personalProducer, CompanyProducer companyProducer) {
        this.repository = repository;
        this.personalProducer = personalProducer;
        this.companyProducer = companyProducer;
    }

    public HolidayResponseDto save(HolidayRequestDto dto) {
        Holiday holiday = HolidayMapper.INSTANCE.saveRequestDtoToHoliday(dto);
        if (dto.getRole().equals("MANAGER")){
            holiday.setStatus(EStatus.ACTIVE);
        }
        repository.save(holiday);
        if (dto.getRole().equals("MANAGER")){
            companyProducer.addHoliday(HolidayModel.builder()
                            .holidayId(holiday.getId())
                            .companyId(holiday.getCompanyId())
                    .build());
        }else if (dto.getRole().equals("PHolidayModelERSONAL")){
            personalProducer.addHoliday(HolidayModel.builder()
                    .holidayId(holiday.getId())
                    .personalId(holiday.getPersonals().get(0))
                    .build());
        }
        return HolidayMapper.INSTANCE.holidayToResponseDto(holiday);
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

    public List<HolidayResponseDto> findAllByCompanyId(Long companyId, EStatus status) {
        return repository.findAllByCompanyId(companyId).stream()
                .filter(holiday -> holiday.getStatus() != EStatus.DELETED)
                .filter(holiday -> status == null || holiday.getStatus() == status)
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

    public HolidayResponseDto update(Long id, HolidayRequestDto dto) {
        Optional<Holiday> optionalExistingHoliday = repository.findById(id);
        if ((optionalExistingHoliday.isPresent())) {
            if (optionalExistingHoliday.get().getStatus() == EStatus.DELETED)
                throw new HolidayServiceException(ErrorType.HOLIDAY_NOT_ACTIVE);
            Holiday existingHoliday = getHoliday(dto, optionalExistingHoliday);
            return HolidayMapper.INSTANCE.holidayToResponseDto(repository.save(existingHoliday));
        }
        throw new HolidayServiceException(ErrorType.HOLIDAY_NOT_FOUND);
    }

    private static Holiday getHoliday(HolidayRequestDto dto, Optional<Holiday> optionalExistingHoliday) {
        Holiday existingHoliday = optionalExistingHoliday.get();
        if (dto.getName() != null) {
            existingHoliday.setName(dto.getName());
        }
        if (dto.getStartDate() != null) {
            existingHoliday.setStartDate(dto.getStartDate());
        }
        if (dto.getEndDate() != null) {
            existingHoliday.setEndDate(dto.getEndDate());
        }
        if (dto.getDescription() != null) {
            existingHoliday.setDescription(dto.getDescription());
        }
        if (dto.getPersonals() != null) {
            existingHoliday.setPersonals(dto.getPersonals());
        }
        return existingHoliday;
    }

    public HolidayResponseDto setStatus(Long id, EStatus status){
        Optional<Holiday> optionalExistingHoliday = repository.findById(id);
        if ((optionalExistingHoliday.isPresent())) {
            if (optionalExistingHoliday.get().getStatus() == EStatus.DELETED)
                throw new HolidayServiceException(ErrorType.HOLIDAY_NOT_ACTIVE);
            Holiday existingHoliday = optionalExistingHoliday.get();
            existingHoliday.setStatus(status);
            return HolidayMapper.INSTANCE.holidayToResponseDto(repository.save(existingHoliday));
        }
        throw new HolidayServiceException(ErrorType.HOLIDAY_NOT_FOUND);
    }
}
