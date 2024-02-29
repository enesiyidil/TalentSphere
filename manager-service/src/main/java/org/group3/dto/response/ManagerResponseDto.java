package org.group3.dto.response;

import lombok.*;
import org.group3.entity.enums.EGender;
import org.group3.entity.enums.EPackage;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class ManagerResponseDto {

    Long id;
    String name;
    String surname;
    String email;
    String phone;
    String photo;
    EGender gender;
    Long companyId;
    String title;
    EPackage ePackage;
    List<Long> personals;
    String updatedDateTime;
    String createdDateTime;



}
