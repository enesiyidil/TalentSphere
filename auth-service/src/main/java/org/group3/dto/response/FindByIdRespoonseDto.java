package org.group3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group3.entity.Enums.ERole;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindByIdRespoonseDto {
    private Long id;
    private String username;
    private String email;
    private String activationCode;
    private ERole role;
}
