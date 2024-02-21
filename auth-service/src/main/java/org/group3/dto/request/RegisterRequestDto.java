package org.group3.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.Enums.ERole;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDto {

    @NotBlank(message = "Username cannot be empty.")
    @Size(min = 3, max = 20,message = "Username must be between 3 and 20 characters.")
    private String username;
    @Email
    private String email;
    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 8,max = 32,message = "Password must be between 8 and 32 characters.")
    private String password;
    @NotBlank(message = "Password cannot be empty.")
    private String rePassword;
    private ERole role;
    private String name;
    private String surname;

}
