package org.group3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.group3.entity.Communication;
import org.group3.entity.Shift;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetInformationResponseDto {
    Long id;
    String name;
    String address;
    List<String> gallery;
    Integer personalNumber;
    List<ShiftGetInformationResponseDto> shifts;
    List<CommunicationGetInformationResponseDto> communications;

}
