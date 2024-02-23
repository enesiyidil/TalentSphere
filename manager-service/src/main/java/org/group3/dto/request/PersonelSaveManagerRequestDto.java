package org.group3.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PersonelSaveManagerRequestDto {
    private Long shiftId;
    private Long companyId;
    private  Long authId;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String title;
    private String photo;
    private Double salary;

}
