package org.group3.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.enums.EStatus;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Company{


    Long id;
    Long managerId;
    String name;
    String address;
    List<String> gallery;
    List<String> payments;
    List<Long> personals;
    List<Communication> communications;
    List<Long> holidays;
    List<Shift> shifts;
    String createdDateTime;
    String updatedDateTime;

    EStatus status;
}
