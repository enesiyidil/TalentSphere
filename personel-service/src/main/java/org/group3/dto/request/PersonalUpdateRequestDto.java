package org.group3.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.repository.entity.enums.EGender;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PersonalUpdateRequestDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String title;
    private String photo;
    private Double salary;
    private EGender gender;
}
