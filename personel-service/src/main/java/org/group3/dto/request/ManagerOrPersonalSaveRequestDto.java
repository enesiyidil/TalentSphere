package org.group3.dto.request;

import lombok.*;
import org.group3.repository.entity.enums.ERole;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ManagerOrPersonalSaveRequestDto {

    String name;
    String surname;
    String email;
    String phone;
    ERole role;

}
