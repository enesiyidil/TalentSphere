package org.group3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.enums.EGender;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindByIdResponseDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String photo;
    private EGender gender;
    private String createdDate;
    private String updatedDate;

}
