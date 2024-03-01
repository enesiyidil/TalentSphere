package org.group3.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.enums.EGender;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRequestDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String photo;
    private EGender gender;
}
