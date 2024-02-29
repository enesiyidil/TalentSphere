package org.group3.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class HolidayFindAllByCompanyIdAndStatusPendingResponseDto {
    Long id;

    String name;

    String startDate;

    String endDate;

    String description;

    String personalName;

    String requestDate;

    String approvalDate;


}
