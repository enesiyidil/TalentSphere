package org.group3.dto.request;

import lombok.*;
import org.group3.entity.enums.EGender;
import org.group3.entity.enums.EPackage;
import org.group3.entity.enums.ERole;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerSaveRequestDto {
    Long companyId;
    Long shiftId;
    String name;
    String surname;
    String email;
    String phone;
    ERole role;
    EGender gender;
    EPackage ePackage;
    String photo;
    Double salary;
    String title;


}
