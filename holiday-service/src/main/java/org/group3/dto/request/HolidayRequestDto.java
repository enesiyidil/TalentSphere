package org.group3.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HolidayRequestDto {

    String name;

    LocalDate startDate;

    LocalDate endDate;

    String description;

    List<Long> personals;

    String role;

}
