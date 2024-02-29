package org.group3.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.enums.ECurrency;
import org.group3.entity.enums.ERole;
import org.group3.entity.enums.EStatus;
import org.group3.entity.enums.EType;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Payment {


    String id;
    Long companyId;
    Long authId;
    BigDecimal amount;
    String dueDate;
    String paymentDate;
    String updatedDate ;
    String description;
    EType type;
    ECurrency currency;
    String expenditureType;
    ERole role;
    EStatus status ;
    String requestDate;
    String approvalDate;

}
