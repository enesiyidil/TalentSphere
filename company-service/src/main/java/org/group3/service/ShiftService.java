package org.group3.service;

import org.group3.dto.request.ShiftSaveRequestDto;
import org.group3.dto.request.ShiftUpdateRequestDto;
import org.group3.dto.response.ShiftResponseDto;
import org.group3.entity.Shift;
import org.group3.entity.enums.EStatus;
import org.group3.exception.CompanyServiceException;
import org.group3.exception.ErrorType;
import org.group3.mapper.ShiftMapper;
import org.group3.repository.ShiftRepository;
import org.group3.utility.ServiceUtility;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShiftService {

    private final ShiftRepository repository;

    private final CompanyService companyService;

    private final ServiceUtility serviceUtility;

    public ShiftService(ShiftRepository repository, CompanyService companyService, ServiceUtility serviceUtility) {
        this.repository = repository;
        this.companyService = companyService;
        this.serviceUtility = serviceUtility;
    }

    public ShiftResponseDto save(ShiftSaveRequestDto dto) {
        Shift shift = repository.save(ShiftMapper.INSTANCE.saveRequestDtoToShift(dto));
        companyService.addShift(shift.getCompanyId(), shift.getId());
        return ShiftMapper.INSTANCE.shiftToResponseDto(shift);
    }

    public ShiftResponseDto findById(Long id) {
        Optional<Shift> optionalShift = repository.findById(id);
        if (optionalShift.isPresent()) {
            if (optionalShift.get().getStatus() == EStatus.DELETED)
                throw new CompanyServiceException(ErrorType.SHIFT_NOT_ACTIVE);
            return ShiftMapper.INSTANCE.shiftToResponseDto(optionalShift.get());
        }
        throw new CompanyServiceException(ErrorType.SHIFT_NOT_FOUND);
    }

    public List<ShiftResponseDto> findAllByCompanyId(Long companyId) {
        return repository.findAllByCompanyId(companyId).stream()
                .filter(shift -> shift.getStatus() == EStatus.ACTIVE)
                .map(ShiftMapper.INSTANCE::shiftToResponseDto)
                .collect(Collectors.toList());
    }

    public Boolean deleteById(Long id) {
        return serviceUtility.softDelete(id, repository);
    }

    public ShiftResponseDto update(ShiftUpdateRequestDto dto) {
        Optional<Shift> optionalExistingShift = repository.findById(dto.getId());
        if ((optionalExistingShift.isPresent())) {
            if (optionalExistingShift.get().getStatus() == EStatus.DELETED)
                throw new CompanyServiceException(ErrorType.SHIFT_NOT_ACTIVE);
            Shift existingShift = optionalExistingShift.get();
            if (dto.getName() != null) {
                existingShift.setName(dto.getName());
            }
            if (dto.getStartTime() != null) {
                existingShift.setStartTime(dto.getStartTime());
            }
            if (dto.getEndTime() != null) {
                existingShift.setEndTime(dto.getEndTime());
            }
            return ShiftMapper.INSTANCE.shiftToResponseDto(repository.save(existingShift));
        }
        throw new CompanyServiceException(ErrorType.SHIFT_NOT_FOUND);
    }

    public void addBreak(Long id, Long breakId) {
        Optional<Shift> optionalExistingShift = repository.findById(id);
        if ((optionalExistingShift.isPresent())) {
            Shift existingShift = optionalExistingShift.get();
            if (existingShift.getBreaks().contains(breakId))
                throw new CompanyServiceException(ErrorType.BREAK_ALREADY_EXISTS);
            existingShift.getBreaks().add(breakId);
            repository.save(existingShift);
        }
        throw new CompanyServiceException(ErrorType.SHIFT_NOT_FOUND);
    }

    public List<ShiftResponseDto> findAllDto() {
        return repository.findAll().stream().map(ShiftMapper.INSTANCE::shiftToResponseDto).collect(Collectors.toList());
    }
}
