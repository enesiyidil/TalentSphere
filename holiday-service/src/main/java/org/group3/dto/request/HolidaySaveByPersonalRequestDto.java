package org.group3.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HolidaySaveByPersonalRequestDto {
    Long companyId;

    String name;

    String startDate;

    String endDate;

    String description;

    Long personalId;

}
