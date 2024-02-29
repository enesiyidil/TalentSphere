package org.group3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.enums.EGender;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class PersonalResponseDto {

    private Long id;
    private Long companyId;
    private Long authId;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String title;
    private String photo;
    private Double salary;
    private EGender gender;
    private String createdDate;
    private String updatedDate;
}
