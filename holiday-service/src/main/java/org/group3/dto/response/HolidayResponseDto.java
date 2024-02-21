package org.group3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.group3.entity.enums.EStatus;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HolidayResponseDto {

    Long companyId;

    String name;

    String startDate;

    String endDate;

    String description;

    List<Long> personals;

    EStatus status;
}
