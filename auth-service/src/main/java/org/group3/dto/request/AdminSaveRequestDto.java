package org.group3.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.group3.entity.Enums.ERole;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminSaveRequestDto {

    String username;
    String email;
    String phone;
    String password;


}
