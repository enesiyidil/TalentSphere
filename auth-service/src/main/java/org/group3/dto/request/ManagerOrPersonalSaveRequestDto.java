package org.group3.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.group3.entity.Enums.ERole;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerOrPersonalSaveRequestDto {

    String name;
    String surname;
    String email;
    String phone;
    ERole role;

}
