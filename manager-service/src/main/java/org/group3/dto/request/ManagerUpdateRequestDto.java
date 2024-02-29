package org.group3.dto.request;

import lombok.*;
import org.group3.entity.enums.EGender;
import org.group3.entity.enums.EPackage;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ManagerUpdateRequestDto {

    Long id;

    String name;

    String surname;

    String email;

    String photo;

    String title;

    String phone;

    EPackage packet;

    EGender gender;
}
