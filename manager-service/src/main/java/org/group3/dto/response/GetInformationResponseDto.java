package org.group3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.enums.EStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetInformationResponseDto {
    Long id;
    Long managerId;
    String name;
    String address;
    List<String> gallery;
    List<Payment> payments;
    List<Personal> personals;
    List<Communication> communications;
    List<HolidayResponseDto> holidays;
    List<Shift> shifts;
    String createdDateTime;
    String updatedDateTime;
    EStatus status;
}
