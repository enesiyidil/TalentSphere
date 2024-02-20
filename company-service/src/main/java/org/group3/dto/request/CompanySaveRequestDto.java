package org.group3.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.group3.entity.Communication;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanySaveRequestDto {

    //Long managerId;

    String name;

    String address;

    Communication phone;
}
