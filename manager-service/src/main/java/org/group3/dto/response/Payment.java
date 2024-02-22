package org.group3.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    BigDecimal amount;

    Long createdDate ;

    Long dueDate;

    Long paymentDate;


    Long updatedDate ;

    String description;

    EType type;

    EStatus status ;
}
