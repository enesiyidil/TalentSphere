package org.group3.dto.request;

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
public class PaymentUpdateRequestDto {

    String id;

    Long companyId;

    BigDecimal amount;

    String dueDate;

    String description;

    EType type;

    ECurrency currency;

    String expenditureType;
}
