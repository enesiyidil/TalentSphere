package org.group3.dto.request;

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
public class CompanySaveRequestDto {

    //Long managerId;

    String name;

    String address;

    String communicationPhone;

    String communicationName;

    List<ShiftSaveRequestDto> shifts;
}
