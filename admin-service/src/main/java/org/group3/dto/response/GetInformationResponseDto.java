package org.group3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetInformationResponseDto {
    Integer managerSize;
    Integer personalSize;
    Integer visitorSize;
    Integer paymentSize;
    Integer commentSize;
    Integer companySize;
}
