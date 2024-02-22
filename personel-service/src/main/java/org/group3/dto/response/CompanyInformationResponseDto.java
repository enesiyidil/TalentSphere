package org.group3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CompanyInformationResponseDto {
    String name;
    String address;
    List<String> gallery;
    Integer personalNumber;
    List<CommunicationGetInformationResponseDto> communications;


}
