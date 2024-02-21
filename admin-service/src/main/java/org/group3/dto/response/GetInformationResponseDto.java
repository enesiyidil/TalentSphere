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
    int managerSize;
    int personalSize;
    int visitorSize;
    int paymentSize;
    int commentSize;
    int companySize;

}