package org.group3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyFindByNameResponseDto {
    Long id;

    Long managerId;

    String name;

    String address;

    List<String> gallery;

    List<String> payments;

    List<Long> personals;

}
