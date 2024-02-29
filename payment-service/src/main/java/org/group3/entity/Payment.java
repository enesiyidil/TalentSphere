package org.group3.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.enums.ECurrency;
import org.group3.entity.enums.ERole;
import org.group3.entity.enums.EStatus;
import org.group3.entity.enums.EType;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "payments")
public class Payment {

    @Id
    String id;

    Long companyId;

    Long authId;

    BigDecimal amount;

    String dueDate;

    String paymentDate;

    String updatedDate;

    String description;

    EType type;

    ECurrency currency;

    String expenditureType;

    ERole role;

    @Builder.Default
    EStatus status = EStatus.PENDING;

    @Builder.Default
    String requestDate= LocalDateTime.now().toString();

    String approvalDate;
}
