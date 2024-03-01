package org.group3.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.Enums.EGender;
import org.group3.entity.Enums.ERole;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDto {

    private String username;
    private String email;
    private String password;
    private String rePassword;
    private String name;
    private String surname;
    private EGender gender;
    private String phone;
    private String photo;

}
