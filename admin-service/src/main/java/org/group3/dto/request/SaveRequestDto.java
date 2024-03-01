package org.group3.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaveRequestDto {


    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private String username;
    private String photo;

}
