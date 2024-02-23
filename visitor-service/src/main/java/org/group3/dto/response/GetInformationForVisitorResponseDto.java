package org.group3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetInformationForVisitorResponseDto {
    String companyName;
    String managerName;
    String address;
    String createdDate;
    Integer paymentNumber;
    Double turnOver;
    Integer commentNumber;
}
