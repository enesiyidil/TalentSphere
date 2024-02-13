package org.group3.service;

import org.group3.dto.request.BreakSaveRequestDto;
import org.group3.dto.request.BreakUpdateRequestDto;
import org.group3.dto.response.BreakResponseDto;
import org.group3.entity.Break;
import org.group3.entity.enums.EStatus;
import org.group3.exception.CompanyServiceException;
import org.group3.exception.ErrorType;
import org.group3.mapper.BreakMapper;
import org.group3.repository.BreakRepository;
import org.group3.utility.ServiceUtility;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BreakService {

    private final BreakRepository repository;

    private final ShiftService shiftService;

    private final ServiceUtility serviceUtility;

    public BreakService(BreakRepository repository, ShiftService shiftService, ServiceUtility serviceUtility) {
        this.repository = repository;
        this.shiftService = shiftService;
        this.serviceUtility = serviceUtility;
    }

    public BreakResponseDto save(BreakSaveRequestDto dto) {
        Break breakEntity = repository.save(BreakMapper.INSTANCE.saveRequestDtoToBreak(dto));
        shiftService.addBreak(breakEntity.getShiftId(), breakEntity.getId());
        return BreakMapper.INSTANCE.breakToResponseDto(breakEntity);
    }

    public BreakResponseDto findById(Long id) {
        Optional<Break> optionalBreak = repository.findById(id);
        if (optionalBreak.isPresent()) {
            if (optionalBreak.get().getStatus() == EStatus.DELETED)
                throw new CompanyServiceException(ErrorType.BREAK_NOT_ACTIVE);
            return BreakMapper.INSTANCE.breakToResponseDto(optionalBreak.get());
        }
        throw new CompanyServiceException(ErrorType.BREAK_NOT_FOUND);
    }

    public List<BreakResponseDto> findAllByShiftId(Long shiftId) {
        return repository.findAllByShiftId(shiftId).stream()
                .filter(breakEntity -> breakEntity.getStatus() == EStatus.ACTIVE)
                .map(BreakMapper.INSTANCE::breakToResponseDto)
                .collect(Collectors.toList());
    }

    public Boolean deleteById(Long id) {
        return serviceUtility.softDelete(id, repository);
    }

    public BreakResponseDto update(BreakUpdateRequestDto dto) {
        Optional<Break> optionalExistingBreak = repository.findById(dto.getId());
        if ((optionalExistingBreak.isPresent())) {
            if (optionalExistingBreak.get().getStatus() == EStatus.DELETED)
                throw new CompanyServiceException(ErrorType.BREAK_NOT_ACTIVE);
            Break existingBreak = optionalExistingBreak.get();
            if (dto.getName() != null) {
                existingBreak.setName(dto.getName());
            }
            if (dto.getStartTime() != null) {
                existingBreak.setStartTime(dto.getStartTime());
            }
            if (dto.getEndTime() != null) {
                existingBreak.setEndTime(dto.getEndTime());
            }
            return BreakMapper.INSTANCE.breakToResponseDto(repository.save(existingBreak));
        }
        throw new CompanyServiceException(ErrorType.BREAK_NOT_FOUND);
    }
}
