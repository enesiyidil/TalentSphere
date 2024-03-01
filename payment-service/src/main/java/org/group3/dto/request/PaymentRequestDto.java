package org.group3.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.enums.ECurrency;
import org.group3.entity.enums.ERole;
import org.group3.entity.enums.EType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentRequestDto {

    Long companyId;
    Long authId;

    BigDecimal amount;

    String dueDate;

    String description;

    EType type;

    ECurrency currency;

    String expenditureType;

    ERole role;
}
