package org.group3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.enums.ECurrency;
import org.group3.entity.enums.EType;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentFindAllByNotApproveResponse {

    String id;
    String nameOfCreator;
    BigDecimal amount;
    String dueDate;
    String updatedDate;
    String description;
    EType type;
    ECurrency currency;
    String expenditureType;
    String requestDate;




}
