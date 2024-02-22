package org.group3.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.dto.response.CommunicationGetInformationResponseDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Company {
    Long id;
    String name;
    String address;
    List<String> gallery;
    Integer personalNumber;
    List<ShiftGetInformationResponseDto> shifts;
    List<CommunicationGetInformationResponseDto> communications;
}
